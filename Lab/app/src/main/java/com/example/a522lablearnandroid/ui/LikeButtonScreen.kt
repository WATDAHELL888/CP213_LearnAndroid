package com.example.a522lablearnandroid.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LikeButtonScreen() {
    // เก็บสถานะว่าตอนปุ่มโดน Like หรือยัง
    var isLiked by remember { mutableStateOf(false) }
    
    // ไว้สำหรับดักจับสถานะว่าปุ่มกำลัง "โดนกด (Pressed)" หรือไม่ เพื่อทำ Scale animation
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 1. Scale Animation: ขยายขนาดปุ่มตอนกำลังกด
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f, // หากกดจะขยาย 10%
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    // 2. Color Animation: เปลี่ยนพื้นหลังเปลี่ยนจากเทา เป็นสีชมพู (สมูทๆ)
    val backgroundColor by animateColorAsState(
        targetValue = if (isLiked) Color(0xFFF48FB1) else Color.LightGray,
        label = "color"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { isLiked = !isLiked },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            modifier = Modifier.scale(scale) // นำ Scale จาก Animation มาใช้งาน
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // 3. AnimatedVisibility: โชว์ไอคอนรูปหัวใจเมื่อ isLiked == true เท่านั้น
                AnimatedVisibility(visible = isLiked) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Heart Icon",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                
                // ข้อความบนปุ่ม
                Text(
                    text = if (isLiked) "Liked" else "Like",
                    color = if (isLiked) Color.White else Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikeButtonScreenPreview() {
    LikeButtonScreen()
}

