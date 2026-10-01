package com.example.ui.screens.interview

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InterviewQuestion
import com.example.data.repository.ReferenceData
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun InterviewScreen(
    onBack: () -> Unit
) {
    var isMockInterviewRunning by remember { mutableStateOf(false) }
    var currentQuestionIdx by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var isInterviewFinished by remember { mutableStateOf(false) }

    val questions = ReferenceData.interviewQuestions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Column {
                Text(
                    text = "Frontend Interview Center",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Junior, Mid-Level & Senior React Interview Prep",
                    style = MaterialTheme.typography.bodySmall,
                    color = ReactCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (!isMockInterviewRunning) {
            // INTERVIEW HOME OVERVIEW
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, ReactCyan.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "🎯 Interactive Mock Technical Interview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ReactCyan
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Simulate real frontend engineering interview questions on reconciliation, closures, useEffect traps, and architecture.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            isMockInterviewRunning = true
                            currentQuestionIdx = 0
                            selectedOption = null
                            score = 0
                            isInterviewFinished = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("start_mock_interview_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = DeepNavyBg, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Start Mock Interview", color = DeepNavyBg, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Browse Common Interview Questions", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()) {
                items(questions) { q ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp)),
                        color = DarkSurface
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Surface(shape = RoundedCornerShape(6.dp), color = DarkSurfaceVariant) {
                                    Text(q.difficulty, color = ReactCyan, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                                Text(q.category, color = Color(0xFF94A3B8), fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(q.question, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.5.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(q.answerSummary, color = Color(0xFFCBD5E1), fontSize = 12.sp, lineHeight = 18.sp)
                        }
                    }
                }
            }
        } else if (!isInterviewFinished) {
            // RUNNING MOCK INTERVIEW
            val currentQ = questions[currentQuestionIdx]
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Question ${currentQuestionIdx + 1} of ${questions.size} • ${currentQ.difficulty}",
                        color = ReactCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentQ.question,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    currentQ.options?.forEachIndexed { optIdx, optText ->
                        val isSelected = selectedOption == optIdx
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) ReactCyan else DarkBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedOption = optIdx },
                            color = if (isSelected) ReactCyan.copy(alpha = 0.2f) else DarkSurfaceVariant
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
                                        text = ('A'.code + optIdx).toChar().toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (isSelected) DeepNavyBg else Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(optText, color = Color.White, fontSize = 13.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (selectedOption != null) {
                                if (selectedOption == currentQ.correctOptionIndex) {
                                    score++
                                }
                                if (currentQuestionIdx < questions.size - 1) {
                                    currentQuestionIdx++
                                    selectedOption = null
                                } else {
                                    isInterviewFinished = true
                                }
                            }
                        },
                        enabled = selectedOption != null,
                        colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (currentQuestionIdx < questions.size - 1) "Next Question →" else "Finish Interview",
                            color = DeepNavyBg,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        } else {
            // INTERVIEW RESULTS SCORECARD
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentEmerald)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Interview Practice Completed!", fontWeight = FontWeight.Bold, color = AccentEmerald, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Practice Score: $score / ${questions.size}", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Great practice! Review deep concepts like reconciliation and closure behavior to polish your explanations for senior technical rounds.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            isMockInterviewRunning = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back to Interview Center", color = DeepNavyBg, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
