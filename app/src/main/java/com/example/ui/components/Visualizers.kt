package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.CodeBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ReactCyan

// 1. COMPONENT TREE VISUALIZER
@Composable
fun ComponentTreeVisualizer(modifier: Modifier = Modifier) {
    var selectedNode by remember { mutableStateOf("App") }

    val nodeDetails = mapOf(
        "App" to "Root Component • State: [theme: 'dark', user: 'Awiskar'] • Children: Header, MainContent, Footer",
        "Header" to "Props: { title: 'React Academy', user: 'Awiskar' } • Renders site logo & navigation",
        "Navigation" to "Props: { activeRoute: '/dashboard' } • 4 links",
        "MainContent" to "Props: { sidebarOpen: false } • Renders 3 dynamic Card components",
        "Card_1" to "Props: { title: 'Components', progress: 100 } • State: [isExpanded: false]",
        "Card_2" to "Props: { title: 'Props & State', progress: 85 } • State: [isExpanded: true]",
        "Card_3" to "Props: { title: 'Hooks Lab', progress: 40 } • State: [isExpanded: false]",
        "Footer" to "Props: { year: 2026, creator: 'Awiskar Acharya' } • Pure presentational"
    )

    Surface(
        modifier = modifier
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
                Text(
                    text = "🌲 Component Tree Visualizer",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = ReactCyan
                )
                Text(
                    text = "Click nodes to inspect",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Tree diagram
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CodeBg, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                TreeNodeItem("App", selectedNode == "App", 0) { selectedNode = "App" }
                TreeNodeItem("├── Header", selectedNode == "Header", 1) { selectedNode = "Header" }
                TreeNodeItem("│   └── Navigation", selectedNode == "Navigation", 2) { selectedNode = "Navigation" }
                TreeNodeItem("├── MainContent", selectedNode == "MainContent", 1) { selectedNode = "MainContent" }
                TreeNodeItem("│   ├── Card (Components)", selectedNode == "Card_1", 2) { selectedNode = "Card_1" }
                TreeNodeItem("│   ├── Card (Props & State)", selectedNode == "Card_2", 2) { selectedNode = "Card_2" }
                TreeNodeItem("│   └── Card (Hooks Lab)", selectedNode == "Card_3", 2) { selectedNode = "Card_3" }
                TreeNodeItem("└── Footer", selectedNode == "Footer", 1) { selectedNode = "Footer" }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = DarkSurfaceVariant,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Inspecting: <$selectedNode />",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Text(
                        text = nodeDetails[selectedNode] ?: "Selected component metadata",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Composable
private fun TreeNodeItem(
    label: String,
    isSelected: Boolean,
    depth: Int,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) ReactCyan.copy(alpha = 0.2f) else Color.Transparent,
        label = "node_bg"
    )
    val textColor = if (isSelected) ReactCyan else Color(0xFFE2E8F0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Monospace,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

// 2. PROPS FLOW VISUALIZER
@Composable
fun PropsFlowVisualizer(modifier: Modifier = Modifier) {
    var titleProp by remember { mutableStateOf("React Masterclass") }
    var badgeColorProp by remember { mutableStateOf("Cyan") }
    var statusProp by remember { mutableStateOf("Active") }
    var likesProp by remember { mutableIntStateOf(128) }

    val colorMap = mapOf(
        "Cyan" to ReactCyan,
        "Green" to AccentEmerald,
        "Purple" to AccentPurple
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
        color = DarkSurface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "⚡ Props Flow: Parent → Props → Child",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = ReactCyan
            )
            Text(
                text = "Change Parent props above; watch the Child Component update instantaneously!",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // PARENT COMPONENT CONTROLS
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = DarkSurfaceVariant
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "1. Parent Component State (Configuring Props):",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = titleProp == "React Masterclass",
                            onClick = { titleProp = "React Masterclass" },
                            label = { Text("Title: Masterclass") },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ReactCyan.copy(alpha = 0.25f))
                        )
                        FilterChip(
                            selected = titleProp == "Frontend Academy",
                            onClick = { titleProp = "Frontend Academy" },
                            label = { Text("Title: Frontend") },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ReactCyan.copy(alpha = 0.25f))
                        )
                        FilterChip(
                            selected = badgeColorProp == "Cyan",
                            onClick = { badgeColorProp = "Cyan" },
                            label = { Text("Color: Cyan") }
                        )
                        FilterChip(
                            selected = badgeColorProp == "Green",
                            onClick = { badgeColorProp = "Green" },
                            label = { Text("Color: Green") }
                        )
                        FilterChip(
                            selected = badgeColorProp == "Purple",
                            onClick = { badgeColorProp = "Purple" },
                            label = { Text("Color: Purple") }
                        )
                    }
                }
            }

            // Downward arrow indicating unidirectional data flow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Data flows down",
                        tint = ReactCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "props={ title: \"$titleProp\", color: \"$badgeColorProp\" }",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = ReactCyan
                    )
                }
            }

            // CHILD COMPONENT OUTPUT
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = CodeBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, colorMap[badgeColorProp] ?: ReactCyan)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "2. Child Component Render Output (<CourseCard />):",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = titleProp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Surface(
                            shape = CircleShape,
                            color = (colorMap[badgeColorProp] ?: ReactCyan).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = statusProp,
                                color = colorMap[badgeColorProp] ?: ReactCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { likesProp++ },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("❤️ Likes: $likesProp (Child Internal State)", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

// 3. STATE CYCLE VISUALIZER
@Composable
fun StateCycleVisualizer(modifier: Modifier = Modifier) {
    var stepIndex by remember { mutableIntStateOf(0) }
    var countValue by remember { mutableIntStateOf(3) }

    val steps = listOf(
        "1. Idle State: Current count is $countValue. UI is in sync with memory.",
        "2. User Action: User taps '+1 Increment' button.",
        "3. setState() Invoked: setCount($countValue + 1) schedules a re-render snapshot.",
        "4. Reconciliation & Diff: React compares VDOM diff (3 -> 4) and patches DOM.",
        "5. Re-render Complete: UI paints 'Count: 4' on screen smoothly!"
    )

    Surface(
        modifier = modifier
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
                Text(
                    text = "🔄 State Update Lifecycle",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = ReactCyan
                )
                Row {
                    IconButton(
                        onClick = {
                            stepIndex = (stepIndex + 1) % steps.size
                            if (stepIndex == 4) countValue++
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Step", tint = ReactCyan)
                    }
                    IconButton(
                        onClick = {
                            stepIndex = 0
                            countValue = 0
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset", tint = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Step timeline blocks
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                steps.forEachIndexed { idx, desc ->
                    val isActive = idx == stepIndex
                    val isPassed = idx < stepIndex
                    val bg = when {
                        isActive -> ReactCyan.copy(alpha = 0.2f)
                        isPassed -> AccentEmerald.copy(alpha = 0.12f)
                        else -> Color(0xFF131D33)
                    }
                    val textCol = when {
                        isActive -> ReactCyan
                        isPassed -> AccentEmerald
                        else -> Color(0xFF64748B)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(bg)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isPassed) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = AccentEmerald, modifier = Modifier.size(16.dp))
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(textCol)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = desc,
                            fontSize = 12.sp,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            color = textCol
                        )
                    }
                }
            }
        }
    }
}

// 4. HOOKS LIFECYCLE SIMULATOR
@Composable
fun HooksLifecycleVisualizer(modifier: Modifier = Modifier) {
    var phase by remember { mutableStateOf("Mount") }

    val phaseDescription = when (phase) {
        "Mount" -> "Component mounts → JSX renders → useEffect runs initial side effect"
        "State Change" -> "State updates → Component re-executes function → JSX updates"
        "Dependency Trigger" -> "useEffect dependency changed → Cleanup previous effect → Run new effect"
        "Unmount" -> "Component removed from DOM → Effect cleanup function executed"
        else -> ""
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
        color = DarkSurface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "⚡ useEffect Lifecycle Flow",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = ReactCyan
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Mount", "State Change", "Dependency Trigger", "Unmount").forEach { p ->
                    FilterChip(
                        selected = phase == p,
                        onClick = { phase = p },
                        label = { Text(p, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ReactCyan.copy(alpha = 0.25f),
                            selectedLabelColor = ReactCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = CodeBg
            ) {
                Text(
                    text = "Phase: $phase\n$phaseDescription",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color(0xFFE2E8F0),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

// 5. VIRTUAL DOM DIFF VISUALIZER
@Composable
fun VirtualDomDiffVisualizer(modifier: Modifier = Modifier) {
    var toggled by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
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
                Text(
                    text = "🔬 Virtual DOM Diff & Reconciliation",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = ReactCyan
                )
                Button(
                    onClick = { toggled = !toggled },
                    colors = ButtonDefaults.buttonColors(containerColor = ReactCyan.copy(alpha = 0.25f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(if (toggled) "State B" else "State A", color = ReactCyan, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // VDOM Snapshot 1
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(CodeBg, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text("Virtual DOM Tree", color = Color(0xFF94A3B8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("<div className=\"app\">", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
                    Text("  <h1>Title</h1>", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
                    Text(
                        text = if (toggled) "  <p className=\"active\">ON</p>" else "  <p>OFF</p>",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = if (toggled) AccentEmerald else ReactCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Text("</div>", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
                }

                // Real DOM Patch
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF0F1B2F), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text("Browser DOM Patch", color = AccentEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("Only updates modified node:", fontSize = 10.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (toggled) "p.textContent = 'ON'\np.className = 'active'" else "p.textContent = 'OFF'\np.className = ''",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = AccentEmerald
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("⚡ 0 full-page reflows", fontSize = 10.5.sp, color = Color(0xFF38BDF8))
                }
            }
        }
    }
}
