package com.example.data.model

enum class VisualizerType {
    NONE,
    COMPONENT_TREE,
    PROPS_FLOW,
    STATE_CYCLE,
    HOOKS_LIFECYCLE,
    VIRTUAL_DOM_DIFF,
    API_TIMELINE,
    REDUCER_FLOW,
    ROUTE_STACK
}

data class CodeExample(
    val title: String,
    val code: String,
    val explanation: String
)

data class LessonStep(
    val stepTitle: String,
    val content: String,
    val visualizerType: VisualizerType = VisualizerType.NONE,
    val sampleCode: String = "",
    val outputPreview: String = ""
)

data class Lesson(
    val id: String,
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val durationMinutes: Int,
    val difficulty: String, // Beginner, Intermediate, Advanced
    val keyTakeaways: List<String>,
    val explanationMarkdown: String,
    val visualizerType: VisualizerType,
    val initialCode: String,
    val interactiveDemoType: String, // e.g. "counter", "props_demo", "effect_timer", "todo", "form", "reducer_cart", "api_fetch"
    val quizQuestion: String,
    val quizOptions: List<String>,
    val quizCorrectIndex: Int,
    val quizExplanation: String
)

data class CurriculumLevel(
    val level: Int,
    val title: String,
    val category: String, // "Foundation", "React Core", "Hooks Lab", "Architecture", "Advanced"
    val description: String,
    val iconName: String,
    val lessons: List<Lesson>
)
