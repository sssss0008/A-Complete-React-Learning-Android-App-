package com.example.data.model

enum class LogLevel {
    INFO,
    WARN,
    ERROR,
    SUCCESS
}

data class ConsoleMessage(
    val level: LogLevel,
    val text: String,
    val timestamp: String
)

data class NetworkRequestEntry(
    val url: String,
    val method: String,
    val status: Int,
    val durationMs: Int,
    val responseJson: String
)

data class ComponentNode(
    val name: String,
    val props: Map<String, String>,
    val state: Map<String, String>,
    val renderCount: Int,
    val children: List<ComponentNode> = emptyList()
)

data class PlaygroundFile(
    val name: String,
    val content: String,
    val isEntry: Boolean = false
)
