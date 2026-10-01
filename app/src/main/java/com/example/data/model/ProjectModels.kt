package com.example.data.model

enum class ProjectLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

data class Milestone(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)

data class ReactProject(
    val id: String,
    val title: String,
    val level: ProjectLevel,
    val category: String,
    val estimatedHours: Int,
    val summary: String,
    val keyFeatures: List<String>,
    val architecturalConcepts: List<String>,
    val milestones: List<Milestone>,
    val starterFiles: Map<String, String>, // filename -> code
    val tags: List<String>
)
