package com.jaqxues.dlancontroller

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.jaqxues.dlancontrollerlib.ApiHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : Activity() {
    private var isChecked = false
    private var currentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleButton.setOnClickListener {
            if (currentJob?.isActive == true) {
                Toast.makeText(this@MainActivity, "Job already active", Toast.LENGTH_LONG).show()
            } else {
                toggleButton.isEnabled = false
                currentJob = GlobalScope.launch (Dispatchers.Main) {
                    try {
                        ApiHandler.changeWLanState(!isChecked)
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                        isChecked = !isChecked
                    } catch (ex: Exception) {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                    toggleButton.isChecked = isChecked
                    toggleButton.isEnabled = true
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            isChecked = ApiHandler.getWLanState()
            toggleButton.isChecked = isChecked
            toggleButton.isEnabled = true
        }
    }
}
