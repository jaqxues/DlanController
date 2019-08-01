package com.jaqxues.dlancontroller

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.jaqxues.dlancontrollerlib.ApiHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : Activity() {
    private var isChecked = false
    private var currentJob: Job? = null
    private lateinit var apiHandler: ApiHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSpinner()
        updateState()

        toggleButton.setOnClickListener {
            if (currentJob?.isActive == true) {
                Toast.makeText(this@MainActivity, "Job already active", Toast.LENGTH_LONG).show()
            } else {
                toggleButton.isEnabled = false
                currentJob = GlobalScope.launch(Dispatchers.Main) {
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
    }

    private fun updateState() {
        toggleButton.isEnabled = false
        GlobalScope.launch(Dispatchers.Main) {
            apiHandler = ApiHandler.getInstance(spinner.selectedItem as String)
            try {
                isChecked = apiHandler.getWLanState()
                toggleButton.isChecked = isChecked
                toggleButton.isEnabled = true
            } catch (ex: Exception) {
                Toast.makeText(this@MainActivity, "Device cannot be reached", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, arrayListOf(
                "192.168.178.20",
                "192.168.178.21",
                "192.168.178.22"
            )
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateState()
            }
        }
    }
}
