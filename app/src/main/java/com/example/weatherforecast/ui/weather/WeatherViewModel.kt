package com.example.weatherforecast.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CityNotFoundException
import com.example.data.ConnectionException
import com.example.data.FavoritesRepository
import com.example.data.InvalidApiKeyException
import com.example.data.RequestRateLimitException
import com.example.data.SettingsRepository
import com.example.data.WeatherRepository
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.entity.dbentity.FavoritesDbEntity
import com.example.weatherforecast.R
import com.example.weatherforecast.SideEffect
import com.example.weatherforecast.model.WeatherState
import com.example.weatherforecast.mappers.toWeatherState
import com.example.weatherforecast.model.SupportedLanguage
import com.example.weatherforecast.model.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val favoritesRepository: FavoritesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _showMessageResEvent = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    val showMessageResEvent = _showMessageResEvent.asStateFlow()

    private val _state = MutableStateFlow<WeatherState?>(null)
    val state: StateFlow<WeatherState?> = _state.asStateFlow()

    init {
        listenWeather()
        test()
    }

    fun test() {
        viewModelScope.launch {
            settingsRepository.getLanguageIndex().collect() {

                refreshWeather()
            }
        }
    }

    fun showMessage(messageRes: Int) {
        _showMessageResEvent.value = SideEffect(messageRes)
    }

    fun refreshWeather() {
        saveLaunch {
            setRefreshing(true)
            _state.value?.let { state ->
                weatherRepository.loadWeatherByCoordinates(
                    Coordinates(
                        lat = state.coordinates.lat,
                        lon = state.coordinates.lon,
                    ),
                    units = Units.values()[settingsRepository.getUnitsIndex().first()].value,
                    language = SupportedLanguage.values()[settingsRepository.getLanguageIndex()
                        .first()].languageValue
                )
            }
        }
    }

    fun getWeatherByCity(city: City) {
        saveLaunch {
            setLoading(true)
            weatherRepository.loadWeatherByCity(
                city = city,
                units = Units.values()[settingsRepository.getUnitsIndex().first()].value,
                language = SupportedLanguage.values()[settingsRepository.getLanguageIndex()
                    .first()].languageValue
            )
        }
    }

    fun getWeatherByCoordinates(coordinates: Coordinates) {
        saveLaunch {
            setLoading(true)
            weatherRepository.loadWeatherByCoordinates(
                coordinates = coordinates,
                units = Units.values()[settingsRepository.getUnitsIndex().first()].value,
                language = SupportedLanguage.values()[settingsRepository.getLanguageIndex()
                    .first()].languageValue
            )
        }
    }

    fun addOrRemoveFromFavorite() {
        viewModelScope.launch {
            _state.value?.let { value ->
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

    private fun listenWeather() {
        weatherRepository.getWeatherFlow().onEach { weather ->
            weather?.toWeatherState()?.let {
                _state.value = it
            }
        }.launchIn(viewModelScope)
    }

    fun setEmptyFieldException(emptyCityError: Boolean) {
        _state.value = _state.value?.copy(emptyCityError = emptyCityError)
    }

    private fun setLoading(loading: Boolean) {
        _state.value = _state.value?.copy(isLoading = loading)
    }

    private fun setRefreshing(refreshing: Boolean) {
        _state.value = _state.value?.copy(isRefreshing = refreshing)
    }

    private fun saveLaunch(block: suspend CoroutineScope.() -> Unit) {
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
                _showMessageResEvent.value = SideEffect(result)
                setRefreshing(false)
                setLoading(false)
            }
        }
    }
}