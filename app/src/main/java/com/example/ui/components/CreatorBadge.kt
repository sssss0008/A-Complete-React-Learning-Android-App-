package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ReactCyan

@Composable
fun CreatorAvatar(
    modifier: Modifier = Modifier,
    size: Int = 56
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF0F2042), Color(0xFF1E3A8A))
                )
            )
            .border(2.dp, ReactCyan.copy(alpha = 0.8f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size((size * 0.85).dp)) {
            val w = this.size.width
            val h = this.size.height

            // Head / Face
            drawCircle(
                color = Color(0xFFFCD34D),
                radius = w * 0.26f,
                center = Offset(w * 0.5f, h * 0.38f)
            )

            // Modern Hair styling
            drawArc(
                color = Color(0xFF1E293B),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.22f, h * 0.12f),
                size = Size(w * 0.56f, h * 0.38f)
            )

            // Glasses
            drawCircle(
                color = Color(0xFF0F172A),
                radius = w * 0.08f,
                center = Offset(w * 0.42f, h * 0.38f)
            )
            drawCircle(
                color = Color(0xFF0F172A),
                radius = w * 0.08f,
                center = Offset(w * 0.58f, h * 0.38f)
            )
            drawLine(
                color = Color(0xFF0F172A),
                start = Offset(w * 0.48f, h * 0.38f),
                end = Offset(w * 0.52f, h * 0.38f),
                strokeWidth = 2.dp.toPx()
            )

            // Developer Jacket / Torso
            drawArc(
                color = Color(0xFF3B82F6),
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.12f, h * 0.58f),
                size = Size(w * 0.76f, h * 0.8f)
            )

            // Inner dark shirt
            drawArc(
                color = Color(0xFF0B132B),
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.35f, h * 0.58f),
                size = Size(w * 0.3f, h * 0.35f)
            )
        }
    }
}

@Composable
fun CreatorCard(
    modifier: Modifier = Modifier,
    showConnectButton: Boolean = true,
    customMessage: String? = null
) {
    val context = LocalContext.current
    val linkedInUrl = "https://www.linkedin.com/in/awiskaracharya/"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(18.dp)),
        color = DarkSurface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CreatorAvatar(size = 58)
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Awiskar Acharya",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified Creator",
                            tint = ReactCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "React Developer • Web Developer • Educator",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.5.sp
                    )
                    Text(
                        text = "Created with ❤️ for developers worldwide",
                        style = MaterialTheme.typography.labelSmall,
                        color = ReactCyan,
                        fontSize = 10.5.sp
                    )
                }
            }

            if (customMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = customMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1),
                    lineHeight = 18.sp
                )
            }

            if (showConnectButton) {
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0A66C2), // Official LinkedIn Blue
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("linkedin_connect_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Connect on LinkedIn",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
