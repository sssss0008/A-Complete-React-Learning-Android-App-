package com.example.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.ui.components.ReactAtomGraphic
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan
import java.util.Calendar

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    onNavigateToLearn: () -> Unit,
    onNavigateToCodeLab: () -> Unit,
    onNavigateToPractice: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToReference: () -> Unit,
    onNavigateToAiTutor: () -> Unit,
    onNavigateToLesson: (String) -> Unit
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greetingTime = when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else -> "Good evening"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // TOP GREETING & REACT ATOM
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$greetingTime, ${userProfile.name}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Continue building your React skills.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ReactCyan
                )
            }
            ReactAtomGraphic(size = 56.dp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // STATS STRIP (Streak, Daily Goal, Mastery)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTile(
                label = "Current Streak",
                value = "${userProfile.streakDays} Days",
                subtext = "🔥 Consistency on fire",
                icon = Icons.Default.LocalFireDepartment,
                accentColor = AccentAmber,
                modifier = Modifier.weight(1f)
            )
            MetricTile(
                label = "Daily Goal",
                value = "14 / ${userProfile.dailyGoalMinutes}m",
                subtext = "70% completed",
                icon = Icons.Default.FitnessCenter,
                accentColor = AccentEmerald,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CURRENT COURSE RESUME CARD
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.dp, ReactCyan.copy(alpha = 0.5f), RoundedCornerShape(18.dp)),
            color = DarkSurface
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(AccentEmerald)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CURRENT COURSE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AccentEmerald
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Level 7: State & useState()",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "State Lifecycle & Functional Updaters",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    ProgressRing(progress = 0.42f, size = 52.dp) {
                        Text(
                            text = "42%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ReactCyan
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { onNavigateToLesson("lvl7_l1") },
                    colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("continue_learning_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = DeepNavyBg,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Continue Learning",
                        color = DeepNavyBg,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // QUICK ACTIONS
        SectionHeader(title = "Quick Actions", subtitle = "Explore the React laboratory ecosystem")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionCard(
                title = "Code Lab",
                subtitle = "Multi-file playground",
                icon = Icons.Default.Code,
                accentColor = ReactCyan,
                onClick = onNavigateToCodeLab
            )
            QuickActionCard(
                title = "Practice Center",
                subtitle = "Challenges & bugs",
                icon = Icons.Default.FitnessCenter,
                accentColor = AccentEmerald,
                onClick = onNavigateToPractice
            )
            QuickActionCard(
                title = "React Projects",
                subtitle = "20+ real apps",
                icon = Icons.Default.Folder,
                accentColor = AccentPurple,
                onClick = onNavigateToProjects
            )
            QuickActionCard(
                title = "Ask AI Tutor",
                subtitle = "Code reviews & hints",
                icon = Icons.Default.Psychology,
                accentColor = AccentAmber,
                onClick = onNavigateToAiTutor
            )
            QuickActionCard(
                title = "Reference",
                subtitle = "13 hooks & cheat sheets",
                icon = Icons.Default.MenuBook,
                accentColor = Color(0xFF38BDF8),
                onClick = onNavigateToReference
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TODAY'S MISSION
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
            color = DarkSurface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            tint = AccentAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Today's Mission",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "2 / 4 Done (50%)",
                        fontSize = 12.sp,
                        color = AccentAmber,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                MissionItem("Learn useEffect dependencies in depth", isCompleted = true)
                MissionItem("Complete 1 React coding challenge", isCompleted = true)
                MissionItem("Fix 1 state mutation bug in Debug Studio", isCompleted = false)
                MissionItem("Experiment with live button builder in Component Lab", isCompleted = false)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SKILL OVERVIEW RADAR BARS
        SectionHeader(
            title = "Skill Mastery Overview",
            subtitle = "Your proficiency across core React pillars",
            actionText = "All 31 Levels",
            onActionClick = onNavigateToLearn
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
            color = DarkSurface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SkillProgressRow("JavaScript for React (ES6+)", 0.85f, AccentEmerald)
                SkillProgressRow("JSX & Templating", 0.90f, ReactCyan)
                SkillProgressRow("Components & Composition", 0.78f, AccentPurple)
                SkillProgressRow("Props & Flow", 0.75f, AccentAmber)
                SkillProgressRow("State & useState", 0.65f, ReactCyan)
                SkillProgressRow("React Hooks Academy", 0.45f, Color(0xFFEC4899))
                SkillProgressRow("API & Async Data Fetching", 0.40f, Color(0xFF38BDF8))
                SkillProgressRow("Routing & Architecture", 0.30f, Color(0xFFF97316))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CREATOR BRANDING CARD
        CreatorCard(
            customMessage = "“The best way to master React isn't merely reading articles—it's writing components, visualizing state cycles, breaking code, and debugging in real-time.” — Awiskar Acharya"
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(148.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        color = DarkSurface
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.5.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun MissionItem(title: String, isCompleted: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (isCompleted) AccentEmerald else Color(0xFF334155),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 13.sp,
            color = if (isCompleted) Color(0xFFE2E8F0) else Color(0xFF94A3B8),
            style = if (isCompleted) MaterialTheme.typography.bodySmall.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough) else MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun SkillProgressRow(name: String, progress: Float, color: Color) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, fontSize = 12.5.sp, color = Color.White, fontWeight = FontWeight.Medium)
            Text(text = "${(progress * 100).toInt()}%", fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = color,
            trackColor = Color(0xFF1E293B)
        )
    }
}
