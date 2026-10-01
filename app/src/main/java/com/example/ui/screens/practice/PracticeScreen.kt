package com.example.ui.screens.practice

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Challenge
import com.example.data.repository.ChallengeData
import com.example.ui.components.CodeSnippetView
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.CodeBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun PracticeScreen(
    completedChallengeIds: Set<String>,
    onMarkCompleted: (String) -> Unit
) {
    var selectedChallenge by remember { mutableStateOf<Challenge?>(null) }
    var subTab by remember { mutableIntStateOf(0) } // 0: Challenges, 1: Debugging Studio, 2: Component Lab

    if (selectedChallenge != null) {
        ChallengeDetailView(
            challenge = selectedChallenge!!,
            isCompleted = completedChallengeIds.contains(selectedChallenge!!.id),
            onBack = { selectedChallenge = null },
            onMarkCompleted = { onMarkCompleted(selectedChallenge!!.id) }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepNavyBg)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Practice Center",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Hands-on coding challenges, bug fixing & component lab",
                style = MaterialTheme.typography.bodySmall,
                color = ReactCyan
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Sub-nav tabs
            TabRow(
                selectedTabIndex = subTab,
                containerColor = DarkSurface,
                contentColor = ReactCyan,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[subTab]),
                        color = ReactCyan
                    )
                }
            ) {
                Tab(
                    selected = subTab == 0,
                    onClick = { subTab = 0 },
                    text = { Text("Challenges (${ChallengeData.allChallenges.size})", fontSize = 12.sp) }
                )
                Tab(
                    selected = subTab == 1,
                    onClick = { subTab = 1 },
                    text = { Text("Debug Studio", fontSize = 12.sp) }
                )
                Tab(
                    selected = subTab == 2,
                    onClick = { subTab = 2 },
                    text = { Text("Component Lab", fontSize = 12.sp) }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (subTab) {
                0 -> {
                    // CHALLENGES LIST
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(ChallengeData.allChallenges, key = { it.id }) { ch ->
                            val isDone = completedChallengeIds.contains(ch.id)
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .border(
                                        1.dp,
                                        if (isDone) AccentEmerald.copy(alpha = 0.5f) else DarkBorder,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable { selectedChallenge = ch },
                                color = DarkSurface
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Surface(
                                                shape = CircleShape,
                                                color = when (ch.difficulty) {
                                                    "Beginner" -> AccentEmerald.copy(alpha = 0.2f)
                                                    "Intermediate" -> ReactCyan.copy(alpha = 0.2f)
                                                    else -> AccentAmber.copy(alpha = 0.2f)
                                                }
                                            ) {
                                                Text(
                                                    text = ch.difficulty,
                                                    fontSize = 10.5.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = when (ch.difficulty) {
                                                        "Beginner" -> AccentEmerald
                                                        "Intermediate" -> ReactCyan
                                                        else -> AccentAmber
                                                    },
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = ch.category,
                                                fontSize = 11.5.sp,
                                                color = Color(0xFF94A3B8)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = ch.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = ch.problemStatement,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFFCBD5E1),
                                            maxLines = 2
                                        )
                                    }

                                    if (isDone) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Done",
                                            tint = AccentEmerald,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.ChevronRight,
                                            contentDescription = "Start",
                                            tint = Color(0xFF64748B),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> DebuggingStudioView()
                2 -> ComponentLabView()
            }
        }
    }
}

@Composable
private fun ChallengeDetailView(
    challenge: Challenge,
    isCompleted: Boolean,
    onBack: () -> Unit,
    onMarkCompleted: () -> Unit
) {
    var userCode by remember { mutableStateOf(challenge.starterCode) }
    var showHint by remember { mutableStateOf(false) }
    var showSolution by remember { mutableStateOf(false) }
    var testsRun by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = challenge.title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Problem Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = DarkSurface,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Problem Statement", fontWeight = FontWeight.Bold, color = ReactCyan, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(challenge.problemStatement, color = Color(0xFFCBD5E1), fontSize = 13.sp, lineHeight = 20.sp)

                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Requirements:", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                    challenge.requirements.forEach { req ->
                        Text("• $req", color = Color(0xFFE2E8F0), fontSize = 12.sp)
                    }

                    if (challenge.initialErrorLog != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF2A1215),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f))
                        ) {
                            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(challenge.initialErrorLog, color = Color(0xFFFCA5A5), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Code Editor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Your Code Solution", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                Row {
                    IconButton(onClick = { showHint = !showHint }) {
                        Icon(Icons.Default.Lightbulb, contentDescription = "Hint", tint = AccentAmber)
                    }
                }
            }

            if (showHint) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = AccentAmber.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber)
                ) {
                    Text("💡 Hint: ${challenge.hint}", color = AccentAmber, fontSize = 12.sp, modifier = Modifier.padding(10.dp))
                }
            }

            OutlinedTextField(
                value = userCode,
                onValueChange = { userCode = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color(0xFFE2E8F0),
                    lineHeight = 18.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CodeBg,
                    unfocusedContainerColor = CodeBg,
                    focusedBorderColor = ReactCyan,
                    unfocusedBorderColor = DarkBorder
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        testsRun = true
                        onMarkCompleted()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("run_test_cases_button")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = DeepNavyBg, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Run Tests & Submit", color = DeepNavyBg, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { showSolution = !showSolution },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(if (showSolution) "Hide" else "Solution", color = Color.White)
                }
            }

            if (testsRun) {
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = AccentEmerald.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentEmerald)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("🎉 All Test Cases Passed!", fontWeight = FontWeight.Bold, color = AccentEmerald)
                        Spacer(modifier = Modifier.height(6.dp))
                        challenge.testCases.forEach { tc ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentEmerald, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(tc.description, color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            if (showSolution) {
                Spacer(modifier = Modifier.height(14.dp))
                CodeSnippetView(code = challenge.solutionCode, title = "Official Solution")
                Spacer(modifier = Modifier.height(8.dp))
                Text(challenge.explanation, color = Color(0xFFCBD5E1), fontSize = 12.5.sp, lineHeight = 18.sp)
            }
        }
    }
}

@Composable
fun DebuggingStudioView() {
    var selectedBugIndex by remember { mutableIntStateOf(0) }
    val bugDemos = listOf(
        "Direct State Mutation" to "const [arr, setArr] = useState([]);\narr.push(item); // ❌ React bails out of render",
        "Infinite useEffect Loop" to "useEffect(() => {\n  setCount(c => c + 1);\n}); // ❌ Missing dependency array",
        "Missing Key Warning" to "items.map(item => <li>{item.name}</li>) // ❌ Missing key={item.id}",
        "Stale State Closure" to "setTimeout(() => {\n  alert('Count: ' + count);\n}, 3000); // ❌ Captured old state snapshot"
    )

    Column {
        Text("React Debugging Studio", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        Text("Inspect common real-world React runtime bugs and learn the fixes.", color = Color(0xFF94A3B8), fontSize = 12.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            bugDemos.forEachIndexed { idx, (title, _) ->
                FilterChip(
                    selected = selectedBugIndex == idx,
                    onClick = { selectedBugIndex = idx },
                    label = { Text(title, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ReactCyan.copy(alpha = 0.25f),
                        selectedLabelColor = ReactCyan
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CodeSnippetView(
            code = bugDemos[selectedBugIndex].second,
            title = "Broken React Code"
        )
    }
}

@Composable
fun ComponentLabView() {
    var buttonVariant by remember { mutableStateOf("Primary") }
    var buttonSize by remember { mutableStateOf("Medium") }
    var buttonDisabled by remember { mutableStateOf(false) }

    Column {
        Text("Component Laboratory", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        Text("Build and preview reusable Material & Tailwind-style React components.", color = Color(0xFF94A3B8), fontSize = 12.sp)

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Live Interactive Preview", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(CodeBg, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val btnBg = when (buttonVariant) {
                        "Primary" -> ReactCyan
                        "Secondary" -> DarkSurfaceVariant
                        "Danger" -> Color(0xFFEF4444)
                        else -> ReactCyan
                    }
                    val btnText = if (buttonVariant == "Primary") DeepNavyBg else Color.White

                    Button(
                        onClick = {},
                        enabled = !buttonDisabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = btnBg,
                            contentColor = btnText
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("React Button ($buttonVariant, $buttonSize)")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Configure Props:", fontWeight = FontWeight.SemiBold, color = Color(0xFF94A3B8), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Primary", "Secondary", "Danger").forEach { v ->
                        FilterChip(
                            selected = buttonVariant == v,
                            onClick = { buttonVariant = v },
                            label = { Text(v, fontSize = 11.5.sp) }
                        )
                    }
                }
            }
        }
    }
}
