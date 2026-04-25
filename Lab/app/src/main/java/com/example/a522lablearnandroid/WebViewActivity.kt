package com.example.a522lablearnandroid

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WebViewScreen()
            }
        }
    }
}

// 1. ViewModel เก็บ State url
class WebViewModel : ViewModel() {
    private val _url = MutableStateFlow("https://www.google.com")
    val url: StateFlow<String> = _url.asStateFlow()

    fun updateUrl(newUrl: String) {
        // เพิ่ม https:// ให้เผื่อคนใช้พิมพ์มาแค่รหัสเว็บ
        val formattedUrl = if (newUrl.startsWith("http://") || newUrl.startsWith("https://")) {
            newUrl
        } else {
            "https://$newUrl"
        }
        _url.value = formattedUrl
    }
}

@Composable
fun WebViewScreen(viewModel: WebViewModel = viewModel()) {
    // ดึงค่า URL ตัวปัจจุบันจาก ViewModel โครงสร้าง
    val currentUrl by viewModel.url.collectAsState()
    
    // State สำหรับเก็บข้อความที่พิมพ์ค้างอยู่ใน TextField
    var inputText by remember { mutableStateOf(currentUrl) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 4. แถบป้อน URL และปุ่ม Go ด้านบนหน้าจอ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Enter URL") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { viewModel.updateUrl(inputText) }
            ) {
                Text("Go")
            }
        }

        // 2. ใช้ AndroidView ครอบ WebView แบบดั้งเดิม
        AndroidView(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            factory = { context ->
                // ส่วนนี้จะถูกเรียกเพียง "ครั้งเดียว" เมื่อ Component ถูกสร้างขึ้น
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    
                    // 3. ป้องกันไม่ให้แอปเปิด Browser ข้างนอก (เช่น Google Chrome) 
                    webViewClient = WebViewClient() 
                    
                    // โหลดหน้าจอแรก
                    loadUrl(currentUrl)
                }
            },
            update = { webView ->
                // Constraint: สาธิตวิธีการใช้งาน update block 
                // Block นี้จะถูกเรียกซ้ำๆ ทุกครั้งที่ตัวแปร State ภายใน block อย่าง "currentUrl" เปลี่ยนแปลงค่า
                
                // เช็คว่า URL ปัจจุบัน ไม่ตรงกับ URL ล่าสุดของ WebView ใช่หรือไม่ ถึงจะโหลดใหม่
                if (webView.url != currentUrl && webView.originalUrl != currentUrl) {
                    webView.loadUrl(currentUrl)
                }
            }
        )
    }
}

