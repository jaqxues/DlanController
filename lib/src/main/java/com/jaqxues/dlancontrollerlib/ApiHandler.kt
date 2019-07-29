package com.jaqxues.dlancontrollerlib

import android.app.Activity
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.lang.Exception

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DlanController.<br>
 * Date: 29.07.2019 - Time 12:39.
 */
object ApiHandler {
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.178.22")
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

    fun changeWLanState(active: Boolean, activity: Activity) {
        GlobalScope.launch(Dispatchers.Main) {
            changeWLanState(active)
            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT)
                .show()
        }
    }
}