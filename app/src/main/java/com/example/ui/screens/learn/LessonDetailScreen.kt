package com.example.ui.screens.learn

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Lesson
import com.example.data.model.VisualizerType
import com.example.ui.components.CodeSnippetView
import com.example.ui.components.ComponentTreeVisualizer
import com.example.ui.components.HooksLifecycleVisualizer
import com.example.ui.components.PropsFlowVisualizer
import com.example.ui.components.StateCycleVisualizer
import com.example.ui.components.VirtualDomDiffVisualizer
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.CodeBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun LessonDetailScreen(
    lesson: Lesson,
    isCompleted: Boolean,
    isBookmarked: Boolean,
    onBack: () -> Unit,
    onToggleBookmark: () -> Unit,
    onMarkCompleted: () -> Unit,
    onOpenInCodeLab: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Lesson", "Visualizer", "Live Preview", "Quiz")

    // Interactive Demo State for Live Preview
    var interactiveCount by remember { mutableIntStateOf(0) }
    var interactiveText by remember { mutableStateOf("React Rocks") }
    var interactiveChecked by remember { mutableStateOf(false) }

    // Quiz State
    var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Level ${lesson.levelNumber}",
                    fontWeight = FontWeight.Bold,
                    color = ReactCyan,
                    fontSize = 14.sp
                )
            }
            Row {
                IconButton(onClick = onToggleBookmark) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) AccentAmber else Color.White
                    )
                }
            }
        }

        // Header Title
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "${lesson.subtitle} • ${lesson.durationMinutes} min read",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DarkSurface,
            contentColor = ReactCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = ReactCyan
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) ReactCyan else Color(0xFF94A3B8)
                        )
                    }
                )
            }
        }

        // Content Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: LESSON EXPLANATION & CODE
                    Column {
                        // Key Takeaways Box
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp)),
                            color = DarkSurface
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🎯 Key Takeaways",
                                    fontWeight = FontWeight.Bold,
                                    color = AccentEmerald,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                lesson.keyTakeaways.forEach { takeaway ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("• ", color = AccentEmerald, fontWeight = FontWeight.Bold)
                                        Text(
                                            text = takeaway,
                                            color = Color(0xFFE2E8F0),
                                            fontSize = 12.5.sp,
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Conceptual Explanation
                        Text(
                            text = "Concept Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = lesson.explanationMarkdown,
                            color = Color(0xFFCBD5E1),
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Starter React Snippet
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Code Implementation",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Button(
                                onClick = { onOpenInCodeLab(lesson.initialCode) },
                                colors = ButtonDefaults.buttonColors(containerColor = ReactCyan.copy(alpha = 0.2f)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Open in Code Lab 🚀", color = ReactCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        CodeSnippetView(
                            code = lesson.initialCode,
                            title = "React Component Snippet"
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                1 -> {
                    // TAB 1: INTERACTIVE VISUALIZER
                    Column {
                        when (lesson.visualizerType) {
                            VisualizerType.COMPONENT_TREE -> ComponentTreeVisualizer()
                            VisualizerType.PROPS_FLOW -> PropsFlowVisualizer()
                            VisualizerType.STATE_CYCLE -> StateCycleVisualizer()
                            VisualizerType.HOOKS_LIFECYCLE -> HooksLifecycleVisualizer()
                            VisualizerType.VIRTUAL_DOM_DIFF -> VirtualDomDiffVisualizer()
                            else -> {
                                StateCycleVisualizer()
                                Spacer(modifier = Modifier.height(12.dp))
                                ComponentTreeVisualizer()
                            }
                        }
                    }
                }
                2 -> {
                    // TAB 2: LIVE SIMULATED PREVIEW
                    Column {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, ReactCyan.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                            color = DarkSurface
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "⚡ Live Component Output",
                                        fontWeight = FontWeight.Bold,
                                        color = ReactCyan,
                                        fontSize = 14.sp
                                    )
                                    IconButton(
                                        onClick = {
                                            interactiveCount = 0
                                            interactiveText = "React Rocks"
                                            interactiveChecked = false
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.Refresh, contentDescription = "Reset Demo", tint = Color.Gray)
                                    }
                                }
                                Text(
                                    text = "Interacting with real state in this simulator",
                                    fontSize = 11.5.sp,
                                    color = Color(0xFF94A3B8)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Render live interactive component
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = CodeBg,
                                    shape = RoundedCornerShape(12.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Component State: count = $interactiveCount",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                            Button(
                                                onClick = { interactiveCount++ },
                                                colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("+ Increment", color = DeepNavyBg, fontWeight = FontWeight.Bold)
                                            }
                                            Button(
                                                onClick = { if (interactiveCount > 0) interactiveCount-- },
                                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("- Decrement", color = Color.White)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(14.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { interactiveChecked = !interactiveChecked }
                                                .background(DarkSurfaceVariant)
                                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = if (interactiveChecked) "✅ Completed State" else "⬜ Pending State",
                                                color = if (interactiveChecked) AccentEmerald else Color.White,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "Console Output: [Render #$interactiveCount complete, 0 errors]",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    color = AccentEmerald
                                )
                            }
                        }
                    }
                }
                3 -> {
                    // TAB 3: QUIZ
                    Column {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, DarkBorder, RoundedCornerShape(14.dp)),
                            color = DarkSurface
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "🧠 Knowledge Check",
                                    fontWeight = FontWeight.Bold,
                                    color = AccentAmber,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = lesson.quizQuestion,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                lesson.quizOptions.forEachIndexed { optIndex, optionText ->
                                    val isSelected = selectedQuizOption == optIndex
                                    val isCorrect = optIndex == lesson.quizCorrectIndex

                                    val bg = when {
                                        quizSubmitted && isCorrect -> AccentEmerald.copy(alpha = 0.2f)
                                        quizSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.2f)
                                        isSelected -> ReactCyan.copy(alpha = 0.2f)
                                        else -> DarkSurfaceVariant
                                    }
                                    val borderCol = when {
                                        quizSubmitted && isCorrect -> AccentEmerald
                                        quizSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444)
                                        isSelected -> ReactCyan
                                        else -> DarkBorder
                                    }

                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .border(1.dp, borderCol, RoundedCornerShape(10.dp))
                                            .clickable(enabled = !quizSubmitted) { selectedQuizOption = optIndex },
                                        color = bg
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isSelected) ReactCyan else Color(0xFF334155)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = ('A'.code + optIndex).toChar().toString(),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isSelected) DeepNavyBg else Color.White
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = optionText,
                                                fontSize = 13.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (!quizSubmitted) {
                                    Button(
                                        onClick = { if (selectedQuizOption != null) quizSubmitted = true },
                                        enabled = selectedQuizOption != null,
                                        colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Submit Answer", color = DeepNavyBg, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    val wasCorrect = selectedQuizOption == lesson.quizCorrectIndex
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = if (wasCorrect) AccentEmerald.copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                text = if (wasCorrect) "🎉 Correct!" else "❌ Not quite.",
                                                fontWeight = FontWeight.Bold,
                                                color = if (wasCorrect) AccentEmerald else Color(0xFFEF4444)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = lesson.quizExplanation,
                                                fontSize = 12.5.sp,
                                                color = Color(0xFFE2E8F0)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Completion Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            tonalElevation = 6.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isCompleted) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentEmerald, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Lesson Completed", color = AccentEmerald, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                } else {
                    Text(
                        text = "Finish lesson to claim XP",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onMarkCompleted,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCompleted) DarkSurfaceVariant else ReactCyan
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("mark_lesson_completed_button")
                ) {
                    Text(
                        text = if (isCompleted) "Completed ✓" else "Mark Complete ✓",
                        color = if (isCompleted) Color(0xFF94A3B8) else DeepNavyBg,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
