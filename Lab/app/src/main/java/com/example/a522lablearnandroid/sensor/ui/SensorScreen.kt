package com.example.a522lablearnandroid.sensor.ui

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a522lablearnandroid.sensor.viewmodel.SensorViewModel
import com.example.a522lablearnandroid.sensor.viewmodel.SensorViewModelFactory

@Composable
fun SensorScreen() {
    val context = LocalContext.current
    val viewModel: SensorViewModel = viewModel(factory = SensorViewModelFactory(context.applicationContext))

    val sensorValue by viewModel.sensorData.collectAsState()
    val locationValue by viewModel.locationData.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        if (fineGranted) {
            viewModel.startLocation()
        } else {
            Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    DisposableEffect(Unit) {
        viewModel.startSensor()
        onDispose {
            viewModel.stopSensor()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Hardware Sensors & MVVM", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(32.dp))

        Text("Accelerometer Value:", style = MaterialTheme.typography.titleLarge)
        Text("X: ${String.format("%.2f", sensorValue.first)}", style = MaterialTheme.typography.bodyLarge)
        Text("Y: ${String.format("%.2f", sensorValue.second)}", style = MaterialTheme.typography.bodyLarge)
        Text("Z: ${String.format("%.2f", sensorValue.third)}", style = MaterialTheme.typography.bodyLarge)
        Text("(Updates automatically)", style = MaterialTheme.typography.bodySmall)
        
        Spacer(modifier = Modifier.height(32.dp))

        Text("GPS Location:", style = MaterialTheme.typography.titleLarge)
        Text("Lat: ${locationValue.first}", style = MaterialTheme.typography.bodyLarge)
        Text("Lng: ${locationValue.second}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                viewModel.startLocation()
            } else {
                permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }) {
            Text("Start Tracking Location")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.stopLocation() }) {
            Text("Stop Tracking Location")
        }
    }
}

