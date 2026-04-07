package com.example.a522lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class TodoSwipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TodoScreen()
            }
        }
    }
}

class TodoViewModel : ViewModel() {
    // 1. ใช้ mutableStateListOf เก็บรายการข้อความเริ่มแรก
    private val _todoList = mutableStateListOf(
        "ซื้อของเข้าบ้าน",
        "จ่ายบิลค่าไฟและค่าน้ำห้อง",
        "ทำการบ้านวิชา Android ให้เสร็จ",
        "ให้อาหารสัตว์เลี้ยงตอนเย็น",
        "ออกกำลังกาย 30 นาที"
    )
    val todoList: List<String> get() = _todoList

    fun removeItem(item: String) {
        _todoList.remove(item)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-Do Swipe", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE))
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEEEEEE))
                .padding(paddingValues)
        ) {
            // สำคัญมากๆ: ใส่ key ใหักับ item เพื่อให้ Compose จัดการ State แอนิเมชันลบได้อย่างถูกต้อง
            items(
                items = viewModel.todoList,
                key = { it }
            ) { item ->
                
                // ควบคุม State สำหรับเช็คว่า ปัดถึงระยะหรือยัง
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.removeItem(item)
                            true
                        } else {
                            false
                        }
                    },
                    positionalThreshold = { totalDistance -> 
                        // กำหนดให้ต้องปัดถึง 75% ของความกว้างหน้าจอถึงจะลบได้
                        totalDistance * 0.75f 
                    }
                )

                // 2. ใช้ SwipeToDismissBox สไตล์ Material 3
                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false, // ไม่ให้ปัดทางขวา
                    backgroundContent = {
                        // ไม่ต้องเช็ค targetValue เพราะเราตั้งค่าให้ปัดซ้ายได้อย่างเดียว
                        // การวาดพื้นหลังสีแดงรอไว้เลย ทำให้แถบแดงโผล่ทันทีที่เริ่มปัด!
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White
                            )
                        }
                    }
                ) {
                    // ส่วนที่เป็นแท็บกระดาษสีขาวปกติ
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(20.dp)
                    ) {
                        Text(text = item, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

