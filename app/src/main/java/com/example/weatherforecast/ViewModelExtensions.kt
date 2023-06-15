package com.example.weatherforecast


import com.example.data.CityNotFoundException
import com.example.data.ConnectionException
import com.example.data.InvalidApiKeyException
import com.example.data.RequestRateLimitException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async


fun CoroutineScope.safeLaunchAsync(block: suspend CoroutineScope.() -> Unit) =
    this.async {
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
        return@async result
    }