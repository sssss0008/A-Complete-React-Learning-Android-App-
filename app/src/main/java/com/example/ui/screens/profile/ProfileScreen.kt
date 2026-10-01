package com.example.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.CreatorCard
import com.example.ui.components.MetricTile
import com.example.ui.components.ProgressRing
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onNavigateToCreator: () -> Unit,
    onNavigateToCertificate: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToInterview: () -> Unit
) {
    val totalLessons = 31
    val completedCount = userProfile.completedLessonIds.size
    val progressFraction = (completedCount.toFloat() / totalLessons.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // TOP USER PROFILE CARD
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.dp, DarkBorder, RoundedCornerShape(18.dp)),
            color = DarkSurface
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(ReactCyan.copy(alpha = 0.2f))
                        .border(2.dp, ReactCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userProfile.name.take(1).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = ReactCyan,
                        fontSize = 24.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = userProfile.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "React Developer • ${userProfile.programmingExperience}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = CircleShape,
                        color = AccentEmerald.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "Level: Intermediate Practitioner",
                            color = AccentEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // STATS TILES
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTile(
                label = "Completed Lessons",
                value = "$completedCount / $totalLessons",
                subtext = "${(progressFraction * 100).toInt()}% Done",
                icon = Icons.Default.EmojiEvents,
                accentColor = ReactCyan,
                modifier = Modifier.weight(1f)
            )
            MetricTile(
                label = "Solved Challenges",
                value = "${userProfile.completedChallengeIds.size}",
                subtext = "Clean Code",
                icon = Icons.Default.LocalFireDepartment,
                accentColor = AccentAmber,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // CERTIFICATE BANNER
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, AccentAmber.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                .clickable(onClick = onNavigateToCertificate)
                .testTag("certificate_card"),
            color = DarkSurface
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AccentAmber.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardMembership,
                            contentDescription = null,
                            tint = AccentAmber,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "React Certificate of Completion",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "View official certificate by Awiskar Acharya",
                            fontSize = 11.5.sp,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = AccentAmber)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // ACHIEVEMENTS BADGES
        Text("Achievement Badges", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BadgeItem("First Component", "⚛️", isUnlocked = true)
                    BadgeItem("7-Day Streak", "🔥", isUnlocked = true)
                    BadgeItem("Hook Master", "⚓", isUnlocked = userProfile.completedLessonIds.contains("lvl12_l1"))
                    BadgeItem("Debug Hero", "🛡️", isUnlocked = userProfile.completedChallengeIds.isNotEmpty())
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BadgeItem("API Explorer", "🌐", isUnlocked = userProfile.completedLessonIds.contains("lvl19_l1"))
                    BadgeItem("VDOM Diff", "🔬", isUnlocked = true)
                    BadgeItem("Component Lab", "🎨", isUnlocked = true)
                    BadgeItem("React Pro", "🏆", isUnlocked = progressFraction > 0.5f)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // QUICK NAVIGATION TILES
        Text("More Centers", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))

        ProfileNavRow("About Creator (Awiskar Acharya)", "Story, philosophy & LinkedIn connect", Icons.Default.Person, onNavigateToCreator)
        ProfileNavRow("Frontend Interview Center", "Mock technical interviews & Q&A", Icons.Default.Psychology, onNavigateToInterview)
        ProfileNavRow("Preferences & Settings", "Theme, reset progress & daily goals", Icons.Default.Settings, onNavigateToSettings)

        Spacer(modifier = Modifier.height(20.dp))

        // CREATOR BRANDING CARD
        CreatorCard()

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BadgeItem(name: String, icon: String, isUnlocked: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(76.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(if (isUnlocked) ReactCyan.copy(alpha = 0.2f) else DarkSurfaceVariant)
                .border(
                    1.dp,
                    if (isUnlocked) ReactCyan else Color(0xFF334155),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontSize = 10.sp,
            color = if (isUnlocked) Color.White else Color(0xFF64748B),
            fontWeight = if (isUnlocked) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Composable
private fun ProfileNavRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = DarkSurface
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ReactCyan.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = ReactCyan, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.5.sp)
                    Text(subtitle, color = Color(0xFF94A3B8), fontSize = 11.sp)
                }
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF64748B))
        }
    }
}
