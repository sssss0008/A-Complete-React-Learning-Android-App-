package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ReactCyan
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ReactAtomGraphic(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    glowColor: Color = ReactCyan,
    isAnimated: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "atom_rotation")
    val rotation by if (isAnimated) {
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 12000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "spin"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    val pulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val rx = this.size.width * 0.44f
        val ry = this.size.height * 0.16f
        val strokeWidth = (this.size.width * 0.024f).coerceAtLeast(2f)

        // Draw 3 orbital ellipses at 0, 60, 120 degrees with subtle rotation
        val angles = listOf(0f, 60f, 120f)
        angles.forEachIndexed { index, angle ->
            val effectiveAngle = angle + (if (index % 2 == 0) rotation * 0.4f else -rotation * 0.4f)
            rotate(effectiveAngle, pivot = center) {
                drawOval(
                    color = glowColor.copy(alpha = 0.85f),
                    topLeft = Offset(center.x - rx, center.y - ry),
                    size = Size(rx * 2f, ry * 2f),
                    style = Stroke(width = strokeWidth)
                )

                // Orbiting electron dot
                val electronAngle = Math.toRadians((rotation * (index + 1.2) * 1.5).toDouble())
                val ex = center.x + (rx * cos(electronAngle)).toFloat()
                val ey = center.y + (ry * sin(electronAngle)).toFloat()
                drawCircle(
                    color = Color.White,
                    radius = strokeWidth * 1.3f,
                    center = Offset(ex, ey)
                )
            }
        }

        // Center Nucleus with glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(glowColor, glowColor.copy(alpha = 0.2f), Color.Transparent),
                center = center,
                radius = rx * 0.45f * pulse
            ),
            radius = rx * 0.4f * pulse,
            center = center
        )

        drawCircle(
            color = glowColor,
            radius = (this.size.width * 0.08f).coerceAtLeast(5f),
            center = center
        )
        drawCircle(
            color = Color.White,
            radius = (this.size.width * 0.03f).coerceAtLeast(2f),
            center = Offset(center.x - rx * 0.02f, center.y - ry * 0.04f)
        )
    }
}
