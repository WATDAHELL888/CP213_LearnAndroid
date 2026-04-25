package com.example.a522lablearnandroid.architecture.mvvm

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MvvmCounterActivity : ComponentActivity() {

    private val viewModel: MvvmCounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MvvmCounterScreen(viewModel)
        }
    }
}

@Composable
fun MvvmCounterScreen(viewModel: MvvmCounterViewModel) {
    val count by viewModel.count.collectAsState()
    val messages by viewModel.messages.collectAsState()

    // 1. ร่าง "สัญญา" (Contract) เตรียมไว้ และบอกว่าถ้า OS ตอบกลับมาจะให้ทำอะไร
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // ผู้ใช้อนุญาต! สั่งเปิดกล้องได้เลย
            println("เย้! ได้สิทธิ์เปิดกล้องแล้ว")
        } else {
            // ผู้ใช้ปฏิเสธ :( ต้องแสดงข้อความบอกผู้ใช้
            println("อดเลย... ผู้ใช้ไม่อนุญาต")
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 2. ปุ่มกดสำหรับ "ยื่นสัญญา" ส่งให้ OS ทำงาน
        Button(onClick = {
            // ยื่นสัญญาขอสิทธิ์ CAMERA แจ้ง OS ให้เอา Dialog ขึ้นมาถามผู้ใช้หน่อย
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("เปิดกล้องถ่ายรูป")
        }


        Text(
            text = "Count: $count",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = { viewModel.onIncrementClicked() }) {
            Text(text = "Increment")
        }

        Button(onClick = { viewModel.onDecrementClicked() }) {
            Text(text = "Decrement")
        }

        Button(onClick = { viewModel.sendMessage("Count",count.toString()) }) {
            Text(text = "Save count")
        }

        LazyColumn() {
            items(messages) { message ->
                Text(message.id+" "+message.name+" "+message.message)
            }
        }
    }
}

