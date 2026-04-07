package com.example.a522lablearnandroid.sensor.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a522lablearnandroid.sensor.tracker.LocationTracker
import com.example.a522lablearnandroid.sensor.tracker.SensorTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SensorViewModel(context: Context) : ViewModel() {
    private val sensorTracker = SensorTracker(context)
    private val locationTracker = LocationTracker(context)

    // StateFlow for Sensor (Accelerometer)
    private val _sensorData = MutableStateFlow(Triple(0f, 0f, 0f))
    val sensorData: StateFlow<Triple<Float, Float, Float>> = _sensorData.asStateFlow()

    // StateFlow for Location
    private val _locationData = MutableStateFlow(Pair(0.0, 0.0))
    val locationData: StateFlow<Pair<Double, Double>> = _locationData.asStateFlow()

    fun startSensor() {
        sensorTracker.startTracking { x, y, z ->
            _sensorData.value = Triple(x, y, z)
        }
    }

    fun stopSensor() {
        sensorTracker.stopTracking()
    }

    fun startLocation() {
        locationTracker.startTracking { lat, lng ->
            _locationData.value = Pair(lat, lng)
        }
    }

    fun stopLocation() {
        locationTracker.stopTracking()
    }

    override fun onCleared() {
        super.onCleared()
        stopSensor()
        stopLocation()
    }
}

class SensorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SensorViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

