package com.jaqxues.dlancontrollerlib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DlanController.<br>
 * Date: 29.07.2019 - Time 12:39.
 */
class ApiHandler(private val ip: String) {
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://$ip")
            .build()
            .create(ApiService::class.java)
    }

    suspend fun changeWLanState(active: Boolean) {
        withContext(Dispatchers.IO) {
            apiService.sendRequest(
                if (active) "on" else "off",
                "/wgl/main.wgl", "std", "", "wireless",
                "home", "", "", ""
            )
        }
    }

    suspend fun getWLanState(): Boolean {
        return withContext(Dispatchers.IO) {
            val state = apiService.getState()
            state.body()?.charStream()?.contains("WLAN status:&nbsp;on")
                ?: throw Exception("Unable to fetch response")
        }
    }

    companion object {
        private val apiServices = mutableMapOf<String, ApiHandler>()

        @JvmStatic
        fun getInstance(ip: String): ApiHandler {
            return if (apiServices.contains(ip))
                apiServices[ip]!!
            else {
                val apiHandler = ApiHandler(ip)
                apiServices[ip] = apiHandler
                apiHandler
            }
        }
    }
}
