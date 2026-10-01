package com.example.data.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean = false,
    val dateUnlocked: String? = null
)

data class UserNote(
    val id: String,
    val title: String,
    val content: String,
    val tag: String,
    val date: String
)

data class UserProfile(
    val name: String = "Learner",
    val programmingExperience: String = "Beginner",
    val reactExperience: String = "Never used React",
    val goals: List<String> = listOf("Learn React fundamentals", "Build web applications"),
    val dailyGoalMinutes: Int = 20,
    val streakDays: Int = 7,
    val completedLessonIds: Set<String> = emptySet(),
    val completedChallengeIds: Set<String> = emptySet(),
    val completedProjectIds: Set<String> = emptySet(),
    val bookmarkedIds: Set<String> = emptySet(),
    val notes: List<UserNote> = emptyList(),
    val isOnboarded: Boolean = false,
    val lastActiveDate: String = "Today"
)
