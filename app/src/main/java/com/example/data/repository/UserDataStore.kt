package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.UserNote
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class UserDataStore(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("learn_react_user_prefs", Context.MODE_PRIVATE)

    private val _userProfile = MutableStateFlow(loadProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private fun loadProfile(): UserProfile {
        val isOnboarded = prefs.getBoolean("is_onboarded", false)
        val name = prefs.getString("user_name", "Awiskar") ?: "Awiskar"
        val progExp = prefs.getString("prog_exp", "Beginner") ?: "Beginner"
        val reactExp = prefs.getString("react_exp", "Never used React") ?: "Never used React"
        val dailyGoal = prefs.getInt("daily_goal", 20)
        val streak = prefs.getInt("streak_days", 7)

        val completedLessons = prefs.getStringSet("completed_lessons", setOf("lvl1_l1", "lvl2_l1")) ?: emptySet()
        val completedChallenges = prefs.getStringSet("completed_challenges", setOf("ch_01")) ?: emptySet()
        val completedProjects = prefs.getStringSet("completed_projects", emptySet()) ?: emptySet()
        val bookmarked = prefs.getStringSet("bookmarked_items", setOf("lvl7_l1")) ?: emptySet()

        val notesJson = prefs.getString("user_notes", "[]") ?: "[]"
        val notes = parseNotes(notesJson)

        val goalsSet = prefs.getStringSet("learning_goals", setOf("Learn React fundamentals", "Build web applications")) ?: emptySet()

        return UserProfile(
            name = name,
            programmingExperience = progExp,
            reactExperience = reactExp,
            goals = goalsSet.toList(),
            dailyGoalMinutes = dailyGoal,
            streakDays = streak,
            completedLessonIds = completedLessons,
            completedChallengeIds = completedChallenges,
            completedProjectIds = completedProjects,
            bookmarkedIds = bookmarked,
            notes = notes,
            isOnboarded = isOnboarded
        )
    }

    fun completeOnboarding(name: String, progExp: String, reactExp: String, goals: List<String>, dailyGoal: Int) {
        prefs.edit()
            .putBoolean("is_onboarded", true)
            .putString("user_name", name.ifBlank { "Awiskar" })
            .putString("prog_exp", progExp)
            .putString("react_exp", reactExp)
            .putStringSet("learning_goals", goals.toSet())
            .putInt("daily_goal", dailyGoal)
            .apply()
        _userProfile.value = loadProfile()
    }

    fun markLessonCompleted(lessonId: String) {
        val current = _userProfile.value.completedLessonIds.toMutableSet()
        current.add(lessonId)
        prefs.edit().putStringSet("completed_lessons", current).apply()
        _userProfile.value = _userProfile.value.copy(completedLessonIds = current)
    }

    fun markChallengeCompleted(challengeId: String) {
        val current = _userProfile.value.completedChallengeIds.toMutableSet()
        current.add(challengeId)
        prefs.edit().putStringSet("completed_challenges", current).apply()
        _userProfile.value = _userProfile.value.copy(completedChallengeIds = current)
    }

    fun markProjectCompleted(projectId: String) {
        val current = _userProfile.value.completedProjectIds.toMutableSet()
        current.add(projectId)
        prefs.edit().putStringSet("completed_projects", current).apply()
        _userProfile.value = _userProfile.value.copy(completedProjectIds = current)
    }

    fun toggleBookmark(id: String) {
        val current = _userProfile.value.bookmarkedIds.toMutableSet()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        prefs.edit().putStringSet("bookmarked_items", current).apply()
        _userProfile.value = _userProfile.value.copy(bookmarkedIds = current)
    }

    fun addNote(title: String, content: String, tag: String) {
        val newNote = UserNote(
            id = "note_${System.currentTimeMillis()}",
            title = title,
            content = content,
            tag = tag,
            date = "Oct 2026"
        )
        val updated = _userProfile.value.notes + newNote
        saveNotes(updated)
        _userProfile.value = _userProfile.value.copy(notes = updated)
    }

    private fun saveNotes(notes: List<UserNote>) {
        val array = JSONArray()
        notes.forEach { n ->
            val obj = JSONObject()
            obj.put("id", n.id)
            obj.put("title", n.title)
            obj.put("content", n.content)
            obj.put("tag", n.tag)
            obj.put("date", n.date)
            array.put(obj)
        }
        prefs.edit().putString("user_notes", array.toString()).apply()
    }

    private fun parseNotes(json: String): List<UserNote> {
        val list = mutableListOf<UserNote>()
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                list.add(
                    UserNote(
                        id = o.optString("id", "n_$i"),
                        title = o.optString("title", "Untitled Note"),
                        content = o.optString("content", ""),
                        tag = o.optString("tag", "General"),
                        date = o.optString("date", "Recently")
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    fun resetProgress() {
        prefs.edit().clear().apply()
        _userProfile.value = loadProfile()
    }
}
