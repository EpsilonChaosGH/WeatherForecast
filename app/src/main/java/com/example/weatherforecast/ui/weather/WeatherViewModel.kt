package com.example.weatherforecast.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.CityNotFoundException
import com.example.data.utils.ConnectionException
import com.example.data.FavoritesRepository
import com.example.data.utils.InvalidApiKeyException
import com.example.data.utils.RequestRateLimitException
import com.example.data.SettingsRepository
import com.example.data.WeatherRepository
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.source.local.entity.FavoritesDbEntity
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherState
import com.example.weatherforecast.mappers.toWeatherState
import com.example.weatherforecast.model.SideEffect
import com.example.weatherforecast.WhileUiSubscribed
import com.example.weatherforecast.model.SupportedLanguage
import com.example.weatherforecast.model.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val favoritesRepository: FavoritesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)
    private val _emptyCityError = MutableStateFlow(false)

    val uiState: StateFlow<WeatherState?> = combine(
        weatherRepository.getWeatherFlow(),
        settingsRepository.getSettingsFlow(),
        _userMessage,
        _isLoading,
        _emptyCityError
    ) { weather, settings, userMessage, isLoading, emptyCityError ->
        weather?.toWeatherState(
            settings,
            userMessage,
            isLoading,
            emptyCityError
        )?.let { return@let it }
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    fun refreshWeather() {
        safeLaunch {
            setLoading(true)
            uiState.value?.let { state ->
                weatherRepository.loadWeatherByCoordinates(
                    Coordinates(
                        lat = state.coordinates.lat,
                        lon = state.coordinates.lon,
                    ),
                    units = Units.values()[state.settingsState.selectedUnitIndex].value,
                    language = SupportedLanguage.values()[state.settingsState.selectedLanguageIndex].languageValue
                )
            }
            setLoading(false)
        }
    }

    fun getWeatherByCity(city: City) {
        safeLaunch {
            setLoading(true)
            val settings = settingsRepository.getSettingsFlow().first()
            weatherRepository.loadWeatherByCity(
                city = city,
                units = Units.values()[settings.selectedUnitIndex].value,
                language = SupportedLanguage.values()[settings.selectedLanguageIndex].languageValue
            )
            setLoading(false)
        }
    }

    fun getWeatherByCoordinates(coordinates: Coordinates) {
        safeLaunch {
            setLoading(true)
            val settings = settingsRepository.getSettingsFlow().first()
            weatherRepository.loadWeatherByCoordinates(
                coordinates = coordinates,
                units = Units.values()[settings.selectedUnitIndex].value,
                language = SupportedLanguage.values()[settings.selectedLanguageIndex].languageValue
            )
            setLoading(false)
        }
    }

    fun addOrRemoveFromFavorite() {
        viewModelScope.launch {
            uiState.value?.let { value ->
                if (value.isFavorites) {
                    favoritesRepository.deleteFromFavoritesById(value.id)
                } else {
                    favoritesRepository.addToFavorites(
                        FavoritesDbEntity(
                            cityId = value.id,
                            city = value.city,
                            lon = value.coordinates.lon,
                            lat = value.coordinates.lat
                        )
                    )
                }
            }
        }
    }

    fun setEmptyFieldException(emptyCityError: Boolean) {
        _emptyCityError.value = emptyCityError
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    private fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val result: Int? = try {
                block.invoke(this)
                null
            } catch (e: ConnectionException) {
                R.string.error_connection
            } catch (e: CityNotFoundException) {
                R.string.error_404_city_not_found
            } catch (e: InvalidApiKeyException) {
                R.string.error_401_invalid_api_key
            } catch (e: RequestRateLimitException) {
                R.string.error_429_request_rate_limit_surpassing
            } catch (e: Exception) {
                R.string.error_internal
            }
            if (result != null) {
                showMessage(result)
                setLoading(false)
            }
        }
    }
}