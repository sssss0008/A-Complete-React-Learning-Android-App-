package com.example.ui.screens.projects

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
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.data.model.ProjectLevel
import com.example.data.model.ReactProject
import com.example.data.repository.ProjectData
import com.example.ui.components.CodeSnippetView
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun ProjectsScreen(
    completedProjectIds: Set<String>,
    onMarkCompleted: (String) -> Unit,
    onOpenInCodeLab: (String) -> Unit
) {
    var selectedProject by remember { mutableStateOf<ReactProject?>(null) }
    var levelFilter by remember { mutableStateOf("All") }

    val filteredProjects = remember(levelFilter) {
        when (levelFilter) {
            "Beginner" -> ProjectData.allProjects.filter { it.level == ProjectLevel.BEGINNER }
            "Intermediate" -> ProjectData.allProjects.filter { it.level == ProjectLevel.INTERMEDIATE }
            "Advanced" -> ProjectData.allProjects.filter { it.level == ProjectLevel.ADVANCED }
            else -> ProjectData.allProjects
        }
    }

    if (selectedProject != null) {
        ProjectDetailView(
            project = selectedProject!!,
            isCompleted = completedProjectIds.contains(selectedProject!!.id),
            onBack = { selectedProject = null },
            onMarkCompleted = { onMarkCompleted(selectedProject!!.id) },
            onOpenInCodeLab = onOpenInCodeLab
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepNavyBg)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "React Project Builder",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Build 20+ production-grade portfolio applications",
                style = MaterialTheme.typography.bodySmall,
                color = ReactCyan
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Beginner", "Intermediate", "Advanced").forEach { lvl ->
                    FilterChip(
                        selected = levelFilter == lvl,
                        onClick = { levelFilter = lvl },
                        label = { Text(lvl, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ReactCyan.copy(alpha = 0.25f),
                            selectedLabelColor = ReactCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProjects, key = { it.id }) { proj ->
                    val isDone = completedProjectIds.contains(proj.id)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                1.dp,
                                if (isDone) AccentEmerald.copy(alpha = 0.5f) else DarkBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedProject = proj },
                        color = DarkSurface
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = when (proj.level) {
                                            ProjectLevel.BEGINNER -> AccentEmerald.copy(alpha = 0.2f)
                                            ProjectLevel.INTERMEDIATE -> ReactCyan.copy(alpha = 0.2f)
                                            ProjectLevel.ADVANCED -> AccentPurple.copy(alpha = 0.2f)
                                        }
                                    ) {
                                        Text(
                                            text = proj.level.name,
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = when (proj.level) {
                                                ProjectLevel.BEGINNER -> AccentEmerald
                                                ProjectLevel.INTERMEDIATE -> ReactCyan
                                                ProjectLevel.ADVANCED -> AccentPurple
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${proj.estimatedHours}h est • ${proj.category}",
                                        fontSize = 11.5.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Completed",
                                        tint = AccentEmerald,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = proj.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = proj.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFCBD5E1),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Tags row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                proj.tags.forEach { tag ->
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = DarkSurfaceVariant
                                    ) {
                                        Text(
                                            text = tag,
                                            fontSize = 10.5.sp,
                                            color = ReactCyan,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
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
}

@Composable
private fun ProjectDetailView(
    project: ReactProject,
    isCompleted: Boolean,
    onBack: () -> Unit,
    onMarkCompleted: () -> Unit,
    onOpenInCodeLab: (String) -> Unit
) {
    val milestoneStatus = remember {
        mutableStateMapOf<String, Boolean>().apply {
            project.milestones.forEach { put(it.id, it.isCompleted) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
    ) {
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
                text = project.title,
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
            Text(
                text = project.summary,
                color = Color(0xFFCBD5E1),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Key Features Checklist
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = DarkSurface,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("✨ Key Features", fontWeight = FontWeight.Bold, color = ReactCyan, fontSize = 13.5.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    project.keyFeatures.forEach { feat ->
                        Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentEmerald, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(feat, color = Color.White, fontSize = 12.5.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Milestones Interactive Checklist
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = DarkSurface,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("🏁 Project Milestones", fontWeight = FontWeight.Bold, color = AccentAmber, fontSize = 13.5.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    project.milestones.forEach { m ->
                        val isChecked = milestoneStatus[m.id] ?: false
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { milestoneStatus[m.id] = !isChecked }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { milestoneStatus[m.id] = it },
                                colors = CheckboxDefaults.colors(checkedColor = AccentEmerald)
                            )
                            Column {
                                Text(m.title, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 13.sp)
                                Text(m.description, color = Color(0xFF94A3B8), fontSize = 11.5.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Starter Code
            val starterCode = project.starterFiles.values.firstOrNull() ?: ""
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Starter Code Template", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                Button(
                    onClick = { onOpenInCodeLab(starterCode) },
                    colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Open in Code Lab 🚀", color = DeepNavyBg, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            CodeSnippetView(code = starterCode, title = "App.jsx")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onMarkCompleted,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCompleted) DarkSurfaceVariant else AccentEmerald
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mark_project_completed_button")
            ) {
                Text(
                    text = if (isCompleted) "Project Completed ✓" else "Mark Project Complete 🏆",
                    color = if (isCompleted) Color(0xFF94A3B8) else DeepNavyBg,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
