package com.example.ui.screens.ai

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.ui.components.ReactAtomGraphic
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.CodeBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.ReactCyan

data class TutorMessage(
    val isUser: Boolean,
    val text: String,
    val codeBlock: String? = null
)

@Composable
fun AiTutorScreen() {
    val messages = remember {
        mutableStateListOf(
            TutorMessage(
                isUser = false,
                text = "Hello! I am your AI React Tutor. Ask me anything about React architecture, debugging infinite loops, hooks, or how Virtual DOM reconciliation works under the hood."
            )
        )
    }

    var inputText by remember { mutableStateOf("") }

    val quickQuestions = listOf(
        "Why does my useEffect loop infinitely?",
        "Compare useState vs useReducer",
        "How does Virtual DOM diffing work?",
        "When should I use useMemo?",
        "Explain controlled vs uncontrolled forms"
    )

    fun sendQuestion(question: String) {
        messages.add(TutorMessage(isUser = true, text = question))

        val reply = when {
            question.contains("useEffect", ignoreCase = true) -> TutorMessage(
                isUser = false,
                text = "An infinite loop in useEffect almost always happens because the effect updates state that triggers a re-render, and that state is also read inside the effect without a proper dependency array guard.\n\nKey Rule:\n1. If you pass `[]` (empty array), the effect runs ONCE on mount.\n2. If you pass `[userId]`, it only runs when `userId` changes.\n3. If you omit the array, it runs after EVERY render.",
                codeBlock = """// ❌ BROKEN: Endless cycle
useEffect(() => {
  setCount(c => c + 1); // Triggers render -> runs effect -> triggers render...
});

// ✅ FIXED: Add empty array or specific dependencies
useEffect(() => {
  // Runs once on component mount
}, []);"""
            )
            question.contains("useReducer", ignoreCase = true) -> TutorMessage(
                isUser = false,
                text = "Use `useState` for simple, independent values (like a single text input or toggle). Use `useReducer` when:\n\n1. Multiple state variables change together.\n2. The next state depends heavily on the previous state.\n3. You want predictable transitions via action objects (like a shopping cart or complex form).",
                codeBlock = """const [state, dispatch] = useReducer(reducer, initialState);
dispatch({ type: 'cart/itemAdded', payload: item });"""
            )
            question.contains("Virtual DOM", ignoreCase = true) || question.contains("diff", ignoreCase = true) -> TutorMessage(
                isUser = false,
                text = "The Virtual DOM is an in-memory lightweight JavaScript object tree mimicking the real browser DOM. During Reconciliation, React diffs two trees with heuristic O(n) rules:\n\n1. Elements of different types produce completely different subtrees.\n2. Sibling elements with unique `key` props are efficiently tracked across position changes and deletions."
            )
            question.contains("useMemo", ignoreCase = true) -> TutorMessage(
                isUser = false,
                text = "`useMemo` caches the calculated result of a function between renders. Use it only when filtering or computing over large datasets (>1000 items) or preserving referential equality for props passed to `React.memo` components. Don't wrap trivial calculations in useMemo; the memory and dependency check overhead will outweigh the benefit!"
            )
            else -> TutorMessage(
                isUser = false,
                text = "Great question! In modern React, write declarative code where UI is a pure projection of state: `UI = f(state)`. Break complex interfaces into small, single-responsibility components and manage data immutably.",
                codeBlock = """// Example Clean Component Pattern
function StatusCard({ title, isActive }) {
  return (
    <div className={isActive ? 'card-active' : 'card-idle'}>
      <h4>{title}</h4>
    </div>
  );
}"""
            )
        }

        messages.add(reply)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyBg)
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(ReactCyan.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    tint = ReactCyan,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "React AI Tutor",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Senior React Engineering Mentor",
                    style = MaterialTheme.typography.bodySmall,
                    color = AccentEmerald
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Prompts Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            quickQuestions.forEach { q ->
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = DarkSurfaceVariant,
                    modifier = Modifier.clickable { sendQuestion(q) }
                ) {
                    Text(
                        text = q,
                        color = Color(0xFFCBD5E1),
                        fontSize = 11.5.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chat Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(if (msg.isUser) 0.8f else 0.95f)
                            .clip(RoundedCornerShape(14.dp)),
                        color = if (msg.isUser) ReactCyan.copy(alpha = 0.2f) else DarkSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (msg.isUser) ReactCyan else DarkBorder
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (msg.isUser) "You" else "⚛️ React Tutor",
                                fontWeight = FontWeight.Bold,
                                color = if (msg.isUser) ReactCyan else AccentEmerald,
                                fontSize = 11.5.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.text,
                                color = Color.White,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                            if (msg.codeBlock != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = CodeBg,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = msg.codeBlock,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.5.sp,
                                        color = Color(0xFFE2E8F0),
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bottom Input Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Ask about hooks, rendering, debugging...", fontSize = 12.sp) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_tutor_input"),
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
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        sendQuestion(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ReactCyan)
                    .testTag("ai_tutor_send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = DeepNavyBg,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
