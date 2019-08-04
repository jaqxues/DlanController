package com.jaqxues.dlancontroller_tv

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jaqxues.dlancontrollerlib.ApiHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var isChecked = false
    private var currentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiHandler = ApiHandler.getInstance("192.168.178.21")

        toggleButton.setOnClickListener {
            if (currentJob?.isActive == true) {
                Toast.makeText(this@MainActivity, "Job already active", Toast.LENGTH_LONG).show()
            } else {
                toggleButton.isEnabled = false
                currentJob = GlobalScope.launch (Dispatchers.Main) {
                    try {
                        apiHandler.changeWLanState(!isChecked)
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
            isChecked = apiHandler.getWLanState()
            toggleButton.isChecked = isChecked
            toggleButton.isEnabled = true
        }
    }
}
