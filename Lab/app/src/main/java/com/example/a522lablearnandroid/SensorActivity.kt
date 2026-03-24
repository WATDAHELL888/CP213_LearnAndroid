package com.example.a522lablearnandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

class SensorTracker(
    context: Context,
    private val onSensorChanged: (Float, Float, Float) -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun startTracking() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopTracking() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            onSensorChanged(x, y, z)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

class LocationTracker(
    private val context: Context,
    private val onLocationChanged: (Double, Double) -> Unit
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var isTracking = false

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                onLocationChanged(location.latitude, location.longitude)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startTracking() {
        if (isTracking) return
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setMinUpdateIntervalMillis(1000)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        isTracking = true
    }

    fun stopTracking() {
        if (isTracking) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            isTracking = false
        }
    }
}

class SensorActivity : ComponentActivity() {

    private val viewModel: SensorViewModel by viewModels()
    private lateinit var sensorTracker: SensorTracker
    private lateinit var locationTracker: LocationTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        sensorTracker = SensorTracker(this) { x, y, z ->
            viewModel.updateAccelerometerData(x, y, z)
        }
        
        locationTracker = LocationTracker(this) { lat, lng ->
            viewModel.updateLocationData(lat, lng)
        }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SensorScreen(
                        viewModel = viewModel,
                        onPermissionGranted = { locationTracker.startTracking() }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorTracker.startTracking()
    }

    override fun onPause() {
        super.onPause()
        sensorTracker.stopTracking()
        locationTracker.stopTracking()
    }
}

@Composable
fun SensorScreen(viewModel: SensorViewModel, onPermissionGranted: () -> Unit) {
    val accelData by viewModel.accelerometerData.collectAsState()
    val locData by viewModel.locationData.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted()
            Toast.makeText(context, "Location Tracking Started", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Accelerometer Data",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "X: ${"%.2f".format(accelData.x)}", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        Text(text = "Y: ${"%.2f".format(accelData.y)}", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        Text(text = "Z: ${"%.2f".format(accelData.z)}", fontSize = 20.sp, modifier = Modifier.padding(4.dp))

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "GPS Location Data",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (locData != null) {
            Text(text = "Lat: ${"%.5f".format(locData!!.latitude)}", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
            Text(text = "Lng: ${"%.5f".format(locData!!.longitude)}", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        } else {
            Text(text = "Waiting for GPS data...", color = androidx.compose.ui.graphics.Color.Gray, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val permission = Manifest.permission.ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                onPermissionGranted()
                Toast.makeText(context, "Location Tracking Started", Toast.LENGTH_SHORT).show()
            } else {
                permissionLauncher.launch(permission)
            }
        }) {
            Text("Start GPS Tracking")
        }
    }
}
