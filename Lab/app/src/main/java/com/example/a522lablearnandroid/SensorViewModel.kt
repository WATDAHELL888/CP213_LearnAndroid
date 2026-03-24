package com.example.a522lablearnandroid

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SensorData(val x: Float, val y: Float, val z: Float)
data class LocationData(val latitude: Double, val longitude: Double)

class SensorViewModel : ViewModel() {
    private val _accelerometerData = MutableStateFlow(SensorData(0f, 0f, 0f))
    val accelerometerData: StateFlow<SensorData> = _accelerometerData.asStateFlow()

    private val _locationData = MutableStateFlow<LocationData?>(null)
    val locationData: StateFlow<LocationData?> = _locationData.asStateFlow()

    fun updateAccelerometerData(x: Float, y: Float, z: Float) {
        _accelerometerData.value = SensorData(x, y, z)
    }

    fun updateLocationData(lat: Double, lng: Double) {
        _locationData.value = LocationData(lat, lng)
    }
}
