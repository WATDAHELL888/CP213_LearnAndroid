package com.example.a522lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SnackbarEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SnackbarScreen()
            }
        }
    }
}

class SnackbarViewModel : ViewModel() {
    // 1. ใช้ Channel สำหรับส่ง One-time Event (Side effect ล้วนๆ ไม่ใช่ UI State แบบปกติ)
    private val _errorChannel = Channel<String>()
    
    // แปลงให้เป็น Flow เพื่อให้ UI ฝั่ง Composable ใช้ฟังก์ชัน .collect ในกระบวนการ Observe ได้
    val errorFlow = _errorChannel.receiveAsFlow()

    fun triggerError() {
        viewModelScope.launch {
            // โยนค่าข้อความ Error เข้าไป
            _errorChannel.send("⚠️ เกิดข้อผิดพลาด: ไม่สามารถเชื่อมต่อกับเซิร์ฟเวอร์ได้!")
        }
    }
}

@Composable
fun SnackbarScreen(viewModel: SnackbarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // 2. สร้าง State สำหรับรับผิดชอบตัว Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // 3. ใช้ LaunchedEffect(Unit) เพื่อทำงานครั้งเดียว (เปิดหน้าจอมาก็จะทำงานค้างไว้ใน Background)
    // ตรงจุดนี้คือการเชื่อมเพื่อช้อนรับของที่มาตาม Channel
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect { errorMessage ->
            // ทันทีที่มี Error หลุดออกมาจากช่องทาง errorFlow ของ ViewModel
            // จะสั่งโชว์ Snackbar ทันที การ showSnackbar เป็น suspend function ดังนั้นมันต้องอยู่ใน coroutine แบบนี้ครับ
            snackbarHostState.showSnackbar(message = errorMessage)
        }
    }

    // มี Scaffold เพื่อเป็นที่อยู่ให้ตัว SnackbarHost
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // 4. ปุ่มสำหรับจำลองให้เกิดการ Error
            Button(onClick = { viewModel.triggerError() }) {
                Text(text = "Trigger Error (Side Effect)")
            }
        }
    }
}

