package com.example.data.model

enum class ChallengeType {
    FIX_BUG,
    COMPLETE_COMPONENT,
    WRITE_HOOK,
    MANAGE_STATE,
    FETCH_API,
    OPTIMIZE_RENDER
}

data class TestCase(
    val description: String,
    val expectedOutput: String,
    val isPassed: Boolean = false
)

data class Challenge(
    val id: String,
    val title: String,
    val difficulty: String, // "Beginner", "Intermediate", "Advanced"
    val category: String, // "JSX", "State", "Hooks", "APIs", "Performance", "Debugging"
    val type: ChallengeType,
    val problemStatement: String,
    val requirements: List<String>,
    val starterCode: String,
    val solutionCode: String,
    val hint: String,
    val explanation: String,
    val testCases: List<TestCase>,
    val initialErrorLog: String? = null
)
