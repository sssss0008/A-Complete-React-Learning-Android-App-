package com.example.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CreatorCard
import com.example.ui.components.ReactAtomGraphic
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun OnboardingScreen(
    onOnboardingFinished: (name: String, progExp: String, reactExp: String, goals: List<String>, dailyGoal: Int) -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var userName by remember { mutableStateOf("Awiskar") }
    var progExp by remember { mutableStateOf("Beginner") }
    var reactExp by remember { mutableStateOf("Never used React") }
    val selectedGoals = remember {
        mutableStateListOf(
            "Learn React fundamentals",
            "Build web applications",
            "Become a frontend developer"
        )
    }
    var scheduleOption by remember { mutableStateOf("20 minutes/day") }
    val dailyMinutes = when (scheduleOption) {
        "10 minutes/day" -> 10
        "20 minutes/day" -> 20
        "30 minutes/day" -> 30
        "1 hour/day" -> 60
        else -> 20
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                (1..7).forEach { i ->
                    val isActive = i <= step
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (i == step) 20.dp else 8.dp, 6.dp)
                            .clip(CircleShape)
                            .background(if (isActive) ReactCyan else Color(0xFF1E293B))
                    )
                }
            }

            AnimatedContent(
                targetState = step,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "onboarding_steps",
                modifier = Modifier.weight(1f, fill = false)
            ) { currentStep ->
                when (currentStep) {
                    1 -> WelcomeStep()
                    2 -> NameStep(name = userName, onNameChange = { userName = it })
                    3 -> ProgrammingExperienceStep(selected = progExp, onSelect = { progExp = it })
                    4 -> ReactExperienceStep(selected = reactExp, onSelect = { reactExp = it })
                    5 -> GoalsStep(selectedGoals = selectedGoals)
                    6 -> ScheduleStep(selected = scheduleOption, onSelect = { scheduleOption = it })
                    7 -> PersonalizedRoadmapStep(
                        name = userName,
                        progExp = progExp,
                        reactExp = reactExp,
                        dailyGoal = scheduleOption
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Action Button
            Button(
                onClick = {
                    if (step < 7) {
                        step++
                    } else {
                        onOnboardingFinished(userName, progExp, reactExp, selectedGoals.toList(), dailyMinutes)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("onboarding_continue_button")
            ) {
                Text(
                    text = if (step == 1) "Start Learning" else if (step == 7) "Start My Roadmap 🚀" else "Continue",
                    color = DeepNavyBg,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = DeepNavyBg
                )
            }
        }
    }
}

@Composable
private fun WelcomeStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ReactAtomGraphic(size = 140.dp)
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Learn React",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            text = "by Awiskar Acharya",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = ReactCyan
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Learn. Build. Practice. Master React.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF94A3B8)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Welcome to the Ultimate React Platform",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Build modern interfaces, understand React deeply, and become a confident React developer through interactive coding labs and visual architecture.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "💡 Don't just read React. Build with React.",
                    color = AccentEmerald,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun NameStep(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "What should we call you?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Your personalized developer journey starts here.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Your Name") },
            placeholder = { Text("e.g. Awiskar") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ReactCyan,
                unfocusedBorderColor = DarkBorder,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("name_input_field")
        )
    }
}

@Composable
private fun ProgrammingExperienceStep(selected: String, onSelect: (String) -> Unit) {
    val options = listOf("Complete Beginner", "Beginner", "Intermediate", "Experienced Developer")
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Programming Experience",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "How much coding experience do you have so far?",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(20.dp))

        options.forEach { opt ->
            SelectableOptionCard(
                title = opt,
                isSelected = selected == opt,
                onClick = { onSelect(opt) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ReactExperienceStep(selected: String, onSelect: (String) -> Unit) {
    val options = listOf(
        "Never used React",
        "Tried React once",
        "Know the basics",
        "Already building React apps"
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "React Experience",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Have you touched React components before?",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(20.dp))

        options.forEach { opt ->
            SelectableOptionCard(
                title = opt,
                isSelected = selected == opt,
                onClick = { onSelect(opt) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun GoalsStep(selectedGoals: MutableList<String>) {
    val allGoals = listOf(
        "Learn React fundamentals",
        "Build websites",
        "Build web applications",
        "Become a frontend developer",
        "Learn Next.js",
        "Prepare for interviews",
        "Build portfolio projects",
        "Improve existing React skills",
        "Learn professional React architecture"
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Learning Goals",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Select all that apply to tailor your curriculum:",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(16.dp))

        allGoals.forEach { g ->
            val isSelected = selectedGoals.contains(g)
            SelectableOptionCard(
                title = g,
                isSelected = isSelected,
                onClick = {
                    if (isSelected) selectedGoals.remove(g) else selectedGoals.add(g)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ScheduleStep(selected: String, onSelect: (String) -> Unit) {
    val schedules = listOf("10 minutes/day", "20 minutes/day", "30 minutes/day", "1 hour/day", "Flexible")
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Daily Learning Schedule",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Consistency beats cramming. Choose your realistic daily pace:",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(20.dp))

        schedules.forEach { s ->
            SelectableOptionCard(
                title = s,
                isSelected = selected == s,
                onClick = { onSelect(s) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun PersonalizedRoadmapStep(
    name: String,
    progExp: String,
    reactExp: String,
    dailyGoal: String
) {
    val roadmapModules = listOf(
        "JavaScript Foundation",
        "React Fundamentals",
        "Components & Tree",
        "Props & State Cycle",
        "Events & Conditional UI",
        "Lists, Keys & Forms",
        "Hooks Academy (13 Hooks)",
        "APIs & Data Fetching",
        "SPA Routing & Nav",
        "State Management",
        "Performance Optimization",
        "React Testing & Debugging",
        "Production Projects"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Your Personalized Roadmap",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Customized for $name • $dailyGoal goal",
            style = MaterialTheme.typography.bodyMedium,
            color = ReactCyan
        )
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "31 Comprehensive Levels Ready:",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                roadmapModules.forEachIndexed { i, mod ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AccentEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${i + 1}. $mod",
                            fontSize = 13.sp,
                            color = Color(0xFFE2E8F0)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectableOptionCard(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) ReactCyan else DarkBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        color = if (isSelected) ReactCyan.copy(alpha = 0.15f) else DarkSurface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else Color(0xFFE2E8F0),
                fontSize = 14.sp
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = ReactCyan,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
