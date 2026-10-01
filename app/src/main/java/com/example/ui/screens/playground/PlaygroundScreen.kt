package com.example.ui.screens.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Tablet
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.CodeBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlaygroundScreen(
    initialCode: String? = null
) {
    // Multi-File Project State
    val files = remember {
        mutableStateMapOf(
            "App.jsx" to (initialCode ?: """import React, { useState } from 'react';
import Header from './Header';
import Card from './Card';

export default function App() {
  const [count, setCount] = useState(0);
  const [tasks, setTasks] = useState(['Learn JSX', 'Explore React Hooks']);

  return (
    <div className="container">
      <Header title="React Playground" />
      <div className="counter-box">
        <h3>Counter State: {count}</h3>
        <button onClick={() => setCount(c => c + 1)}>Increment</button>
        <button onClick={() => setCount(0)}>Reset</button>
      </div>
      <div className="cards-grid">
        {tasks.map((task, i) => (
          <Card key={i} title={task} active={i === 0} />
        ))}
      </div>
    </div>
  );
}"""),
            "Header.jsx" to """import React from 'react';

export default function Header({ title }) {
  return (
    <header className="site-header">
      <div className="logo">⚛️ {title}</div>
      <nav><a href="#home">Home</a> • <a href="#lab">Lab</a></nav>
    </header>
  );
}""",
            "Card.jsx" to """import React, { useState } from 'react';

export default function Card({ title, active }) {
  const [likes, setLikes] = useState(0);

  return (
    <div className={`card ${'$'}{active ? 'active' : ''}`}>
      <h4>{title}</h4>
      <button onClick={() => setLikes(l => l + 1)}>
        ❤️ {likes} Likes
      </button>
    </div>
  );
}""",
            "styles.css" to """.container { padding: 16px; font-family: sans-serif; }
.site-header { display: flex; justify-content: space-between; border-bottom: 1px solid #334155; }
.counter-box { background: #0F172A; padding: 12px; border-radius: 8px; margin: 12px 0; }
.card { border: 1px solid #38BDF8; padding: 10px; border-radius: 6px; }
.card.active { border-color: #10B981; }"""
        )
    }

    var activeFileName by remember { mutableStateOf("App.jsx") }
    var codeContent by remember { mutableStateOf(files[activeFileName] ?: "") }

    // Screen Main Tabs: 0: Editor & Preview, 1: Live Interactive Preview, 2: Inspector
    var mainViewMode by remember { mutableIntStateOf(0) } // 0 = Split/Editor, 1 = Preview Full, 2 = Inspector

    // Viewport Mode
    var viewportMode by remember { mutableStateOf("Mobile") } // Mobile, Tablet, Desktop

    // Simulated Interactive Running App State
    var simulatedCount by remember { mutableIntStateOf(0) }
    val simulatedTasks = remember { mutableStateListOf("Learn JSX", "Explore React Hooks") }
    var newTaskInput by remember { mutableStateOf("") }
    var isDarkTheme by remember { mutableStateOf(true) }
    var renderCounter by remember { mutableIntStateOf(1) }

    // Terminal State
    var activeTerminalTab by remember { mutableIntStateOf(0) } // 0: Console, 1: Problems, 2: Network, 3: Tests
    val consoleLogs = remember {
        mutableStateListOf(
            "[HMR] React Fast Refresh connected",
            "ReactDOM.createRoot() mounted into #root",
            "App initial render complete [State: count=0, tasks=2]"
        )
    }

    fun log(msg: String) {
        val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        consoleLogs.add("[$time] $msg")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
    ) {
        // TOP TOOLBAR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⚛️ Code Lab",
                        fontWeight = FontWeight.Bold,
                        color = ReactCyan,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    // View Mode Switcher
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkSurfaceVariant)
                            .padding(2.dp)
                    ) {
                        TabPill("Editor", mainViewMode == 0) { mainViewMode = 0 }
                        TabPill("Preview", mainViewMode == 1) { mainViewMode = 1 }
                        TabPill("Inspector", mainViewMode == 2) { mainViewMode = 2 }
                    }
                }

                // Run & Action Buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            files[activeFileName] = codeContent
                            renderCounter++
                            log("Compiled and refreshed: $activeFileName [Render #${renderCounter}]")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("run_code_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = DeepNavyBg,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Run", color = DeepNavyBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    IconButton(
                        onClick = {
                            simulatedCount = 0
                            simulatedTasks.clear()
                            simulatedTasks.addAll(listOf("Learn JSX", "Explore React Hooks"))
                            renderCounter = 1
                            log("Environment reset to initial state")
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset State", tint = Color.Gray)
                    }
                }
            }
        }

        // FILE TABS ROW
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A0F1D))
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            files.keys.forEach { fileName ->
                val isSelected = activeFileName == fileName
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable {
                            files[activeFileName] = codeContent
                            activeFileName = fileName
                            codeContent = files[fileName] ?: ""
                        },
                    color = if (isSelected) DarkSurfaceVariant else Color.Transparent,
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, ReactCyan.copy(alpha = 0.5f)) else null
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (fileName.endsWith(".jsx")) "⚛️ " else "🎨 ",
                            fontSize = 11.sp
                        )
                        Text(
                            text = fileName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }

        // MAIN BODY AREA
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (mainViewMode) {
                0 -> {
                    // EDITOR MODE
                    Column(modifier = Modifier.fillMaxSize()) {
                        OutlinedTextField(
                            value = codeContent,
                            onValueChange = {
                                codeContent = it
                                files[activeFileName] = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .testTag("code_editor_field"),
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                color = Color(0xFFE2E8F0),
                                lineHeight = 18.sp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CodeBg,
                                unfocusedContainerColor = CodeBg,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = ReactCyan
                            )
                        )

                        // Quick Format & Info Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF0F172A))
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ln ${codeContent.lines().size}, Col ${codeContent.length} • UTF-8 • JSX",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color(0xFF64748B)
                            )
                            Row {
                                Text(
                                    text = "Prettier Format",
                                    fontSize = 11.sp,
                                    color = ReactCyan,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .clickable {
                                            codeContent = codeContent.trim()
                                            files[activeFileName] = codeContent
                                            log("Code formatted cleanly")
                                        }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // LIVE PREVIEW MODE
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Viewport Selector
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceVariant)
                                .padding(2.dp)
                        ) {
                            ViewportPill("Mobile", Icons.Default.PhoneAndroid, viewportMode == "Mobile") { viewportMode = "Mobile" }
                            ViewportPill("Tablet", Icons.Default.Tablet, viewportMode == "Tablet") { viewportMode = "Tablet" }
                            ViewportPill("Desktop", Icons.Default.Computer, viewportMode == "Desktop") { viewportMode = "Desktop" }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Device Frame simulation
                        val deviceWidth = when (viewportMode) {
                            "Mobile" -> 340.dp
                            "Tablet" -> 480.dp
                            else -> 600.dp
                        }

                        Surface(
                            modifier = Modifier
                                .widthIn(max = deviceWidth)
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, ReactCyan.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                            color = if (isDarkTheme) Color(0xFF0F172A) else Color(0xFFF8FAFC)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                // Simulated Header
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "⚛️ React App",
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDarkTheme) Color.White else Color(0xFF0F172A),
                                        fontSize = 16.sp
                                    )
                                    IconButton(
                                        onClick = {
                                            isDarkTheme = !isDarkTheme
                                            log("Theme context toggled: ${if (isDarkTheme) "Dark" else "Light"}")
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Text(if (isDarkTheme) "🌙" else "☀️", fontSize = 16.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Interactive Counter Box
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFE2E8F0),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = "Counter State: $simulatedCount",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = if (isDarkTheme) Color.White else Color(0xFF0F172A)
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Button(
                                                onClick = {
                                                    simulatedCount++
                                                    log("setCount($simulatedCount) triggered state update")
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = ReactCyan),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("+ Increment", color = DeepNavyBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            }
                                            Button(
                                                onClick = {
                                                    simulatedCount = 0
                                                    log("Counter reset to 0")
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("Reset", color = Color.White, fontSize = 12.sp)
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Interactive Task List
                                Text(
                                    text = "Dynamic Task List (${simulatedTasks.size}):",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isDarkTheme) Color.White else Color(0xFF0F172A)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedTextField(
                                        value = newTaskInput,
                                        onValueChange = { newTaskInput = it },
                                        placeholder = { Text("New task...", fontSize = 12.sp) },
                                        singleLine = true,
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                                            unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            if (newTaskInput.isNotBlank()) {
                                                simulatedTasks.add(newTaskInput)
                                                log("Task added: '$newTaskInput' [New length: ${simulatedTasks.size}]")
                                                newTaskInput = ""
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Add", color = DeepNavyBg, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                simulatedTasks.forEachIndexed { index, task ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        color = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFE2E8F0),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "${index + 1}. $task",
                                                color = if (isDarkTheme) Color.White else Color(0xFF0F172A),
                                                fontSize = 13.sp
                                            )
                                            IconButton(
                                                onClick = {
                                                    val removed = simulatedTasks.removeAt(index)
                                                    log("Removed item: '$removed'")
                                                },
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // COMPONENT INSPECTOR MODE
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "🔍 React DevTools Component Inspector",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ReactCyan
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        InspectorCard(
                            componentName = "App",
                            props = emptyMap(),
                            state = mapOf(
                                "count" to "$simulatedCount",
                                "tasks" to "${simulatedTasks.size} items",
                                "theme" to if (isDarkTheme) "dark" else "light"
                            ),
                            renderCount = renderCounter,
                            hooks = listOf("useState(0)", "useState([])", "useContext(ThemeContext)")
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InspectorCard(
                            componentName = "Header",
                            props = mapOf("title" to "\"React Playground\""),
                            state = emptyMap(),
                            renderCount = renderCounter,
                            hooks = emptyList()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InspectorCard(
                            componentName = "Card (Item 1)",
                            props = mapOf("title" to "\"Learn JSX\"", "active" to "true"),
                            state = mapOf("likes" to "0"),
                            renderCount = renderCounter,
                            hooks = listOf("useState(0)")
                        )
                    }
                }
            }
        }

        // BOTTOM TERMINAL PANEL
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .border(1.dp, DarkBorder, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            color = DarkSurface
        ) {
            Column {
                // Terminal Tabs
                TabRow(
                    selectedTabIndex = activeTerminalTab,
                    containerColor = Color(0xFF0F172A),
                    contentColor = ReactCyan,
                    modifier = Modifier.height(34.dp),
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[activeTerminalTab]),
                            color = ReactCyan
                        )
                    }
                ) {
                    val termTabs = listOf("Console (${consoleLogs.size})", "Problems (0)", "Network", "Tests (3/3)")
                    termTabs.forEachIndexed { i, title ->
                        Tab(
                            selected = activeTerminalTab == i,
                            onClick = { activeTerminalTab = i },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (activeTerminalTab == i) FontWeight.Bold else FontWeight.Normal,
                                    color = if (activeTerminalTab == i) ReactCyan else Color(0xFF94A3B8)
                                )
                            }
                        )
                    }
                }

                // Terminal Content
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CodeBg)
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (activeTerminalTab) {
                        0 -> {
                            // Console Tab
                            Column {
                                consoleLogs.takeLast(10).forEach { line ->
                                    Text(
                                        text = line,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.5.sp,
                                        color = if (line.contains("Error")) Color(0xFFEF4444) else Color(0xFF34D399),
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                        1 -> {
                            // Problems Tab
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = AccentEmerald, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("No ESLint or JSX syntax problems found in workspace", color = AccentEmerald, fontSize = 12.sp)
                            }
                        }
                        2 -> {
                            // Network Tab
                            Column {
                                Text("GET /api/tasks 200 OK (24ms) • 1.2 KB", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = AccentEmerald)
                                Text("POST /api/telemetry 201 Created (18ms) • 420 B", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color(0xFF38BDF8))
                            }
                        }
                        3 -> {
                            // Tests Tab
                            Column {
                                Text("✓ renders Counter component without crashing", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = AccentEmerald)
                                Text("✓ increments count on button click", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = AccentEmerald)
                                Text("✓ adds new task item to list", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = AccentEmerald)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabPill(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) ReactCyan else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) DeepNavyBg else Color(0xFF94A3B8)
        )
    }
}

@Composable
private fun ViewportPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) ReactCyan.copy(alpha = 0.25f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) ReactCyan else Color(0xFF94A3B8),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) ReactCyan else Color(0xFF94A3B8)
        )
    }
}

@Composable
private fun InspectorCard(
    componentName: String,
    props: Map<String, String>,
    state: Map<String, String>,
    renderCount: Int,
    hooks: List<String>
) {
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
                Text(
                    text = "<$componentName />",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = ReactCyan,
                    fontSize = 14.sp
                )
                Text(
                    text = "Renders: $renderCount",
                    fontSize = 11.sp,
                    color = AccentAmber
                )
            }

            if (props.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("Props:", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8))
                props.forEach { (k, v) ->
                    Text("  $k = $v", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
                }
            }

            if (state.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("State:", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8))
                state.forEach { (k, v) ->
                    Text("  $k: $v", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = AccentEmerald)
                }
            }

            if (hooks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("Hooks:", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8))
                hooks.forEach { h ->
                    Text("  ⚓ $h", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color(0xFF38BDF8))
                }
            }
        }
    }
}
