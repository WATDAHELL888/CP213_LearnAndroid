package com.example.a522lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

class DonutChartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DonutChart(
                        proportions = listOf(30f, 40f, 30f),
                        colors = listOf(
                            Color(0xFFE91E63), // Pink
                            Color(0xFF2196F3), // Blue
                            Color(0xFFFFC107)  // Amber
                        ),
                        modifier = Modifier
                            .size(250.dp)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    require(proportions.size == colors.size) { 
        "จำนวนข้อมูล Proportions กับ Colors ต้องเท่ากัน" 
    }

    val total = proportions.sum()
    val sweepAngles = proportions.map { (it / total) * 360f }
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500, 
                easing = FastOutSlowInEasing
            )
        )
    }

    Canvas(modifier = modifier) {
        val donutStrokeWidth = 45.dp.toPx()
        val currentMaxSweep = 360f * animationProgress.value
        
        var startAngle = -90f
        var accumulatedSweep = 0f

        for (i in sweepAngles.indices) {
            val sectionSweep = sweepAngles[i]
            val allowedSweep = (currentMaxSweep - accumulatedSweep).coerceIn(0f, sectionSweep)

            if (allowedSweep > 0) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = allowedSweep,
                    useCenter = false, 
                    style = Stroke(
                        width = donutStrokeWidth,
                        cap = StrokeCap.Butt 
                    )
                )
            }
            startAngle += sectionSweep
            accumulatedSweep += sectionSweep
        }
    }
}

