package com.example.data.model

data class HookDoc(
    val name: String,
    val signature: String,
    val summary: String,
    val whenToUse: String,
    val syntaxExample: String,
    val commonMistakes: List<String>,
    val bestPractices: List<String>
)

data class CheatSheet(
    val category: String,
    val title: String,
    val description: String,
    val snippets: List<Pair<String, String>> // Title -> Code snippet
)

data class GlossaryItem(
    val term: String,
    val category: String,
    val definition: String,
    val example: String
)

data class InterviewQuestion(
    val id: String,
    val question: String,
    val category: String, // "Fundamentals", "Hooks & State", "Architecture & Performance"
    val difficulty: String, // "Junior", "Mid", "Senior"
    val answerSummary: String,
    val detailedAnswer: String,
    val codeSnippet: String? = null,
    val options: List<String>? = null,
    val correctOptionIndex: Int? = null
)
