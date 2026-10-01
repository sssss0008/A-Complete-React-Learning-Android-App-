package com.example.ui.screens.reference

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HookDoc
import com.example.data.repository.ReferenceData
import com.example.ui.components.CodeSnippetView
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.CodeBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

@Composable
fun ReferenceScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryTab by remember { mutableIntStateOf(0) } // 0: Hooks, 1: Cheat Sheets, 2: Glossary
    var expandedHookName by remember { mutableStateOf<String?>("useState") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
            .padding(16.dp)
    ) {
        Text(
            text = "React Reference Center",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Documentation, cheat sheets, and terminology glossary",
            style = MaterialTheme.typography.bodySmall,
            color = ReactCyan
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search hooks, syntax, concepts...", fontSize = 12.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF94A3B8))
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reference_search_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ReactCyan,
                unfocusedBorderColor = DarkBorder,
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tab Navigation
        TabRow(
            selectedTabIndex = selectedCategoryTab,
            containerColor = DarkSurface,
            contentColor = ReactCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedCategoryTab]),
                    color = ReactCyan
                )
            }
        ) {
            val tabs = listOf("Hooks Index (13)", "Cheat Sheets", "Glossary")
            tabs.forEachIndexed { i, title ->
                Tab(
                    selected = selectedCategoryTab == i,
                    onClick = { selectedCategoryTab = i },
                    text = { Text(title, fontSize = 12.sp, fontWeight = if (selectedCategoryTab == i) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedCategoryTab) {
            0 -> {
                // HOOKS LIST
                val filteredHooks = remember(searchQuery) {
                    if (searchQuery.isBlank()) ReferenceData.allHooks
                    else ReferenceData.allHooks.filter {
                        it.name.contains(searchQuery, ignoreCase = true) || it.summary.contains(searchQuery, ignoreCase = true)
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredHooks, key = { it.name }) { hook ->
                        val isExpanded = expandedHookName == hook.name
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, if (isExpanded) ReactCyan else DarkBorder, RoundedCornerShape(14.dp))
                                .clickable { expandedHookName = if (isExpanded) null else hook.name },
                            color = DarkSurface
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = hook.name,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        color = ReactCyan,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = if (isExpanded) "▲ Hide" else "▼ Details",
                                        fontSize = 11.5.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = hook.summary,
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 12.5.sp
                                )

                                if (isExpanded) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text("Signature:", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 11.5.sp)
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = CodeBg,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = hook.signature,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            color = AccentEmerald,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("When to use:", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 11.5.sp)
                                    Text(hook.whenToUse, color = Color(0xFFE2E8F0), fontSize = 12.sp)

                                    Spacer(modifier = Modifier.height(10.dp))
                                    CodeSnippetView(code = hook.syntaxExample, title = "${hook.name} Usage")

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("Common Mistakes to Avoid:", fontWeight = FontWeight.Bold, color = Color(0xFFEF4444), fontSize = 11.5.sp)
                                    hook.commonMistakes.forEach { cm ->
                                        Text("⚠️ $cm", color = Color(0xFFFCA5A5), fontSize = 11.5.sp, modifier = Modifier.padding(vertical = 2.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                // CHEAT SHEETS
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(ReferenceData.cheatSheets) { cs ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, DarkBorder, RoundedCornerShape(14.dp)),
                            color = DarkSurface
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(cs.title, fontWeight = FontWeight.Bold, color = ReactCyan, fontSize = 14.sp)
                                Text(cs.description, color = Color(0xFF94A3B8), fontSize = 11.5.sp)
                                Spacer(modifier = Modifier.height(10.dp))

                                cs.snippets.forEach { (snippetTitle, snippetCode) ->
                                    Text(snippetTitle, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CodeSnippetView(code = snippetCode)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
            2 -> {
                // GLOSSARY
                val filteredGlossary = remember(searchQuery) {
                    if (searchQuery.isBlank()) ReferenceData.glossary
                    else ReferenceData.glossary.filter {
                        it.term.contains(searchQuery, ignoreCase = true) || it.definition.contains(searchQuery, ignoreCase = true)
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredGlossary) { item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp)),
                            color = DarkSurface
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(item.term, fontWeight = FontWeight.Bold, color = ReactCyan, fontSize = 14.sp)
                                    Surface(shape = RoundedCornerShape(4.dp), color = DarkSurfaceVariant) {
                                        Text(item.category, color = Color(0xFF94A3B8), fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(item.definition, color = Color(0xFFE2E8F0), fontSize = 12.5.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(item.example, fontFamily = FontFamily.Monospace, color = AccentEmerald, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
