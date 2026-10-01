package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CurriculumData
import com.example.data.repository.UserDataStore
import com.example.ui.screens.ai.AiTutorScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.interview.InterviewScreen
import com.example.ui.screens.learn.CurriculumScreen
import com.example.ui.screens.learn.LessonDetailScreen
import com.example.ui.screens.onboarding.OnboardingScreen
import com.example.ui.screens.playground.PlaygroundScreen
import com.example.ui.screens.practice.PracticeScreen
import com.example.ui.screens.profile.CertificateScreen
import com.example.ui.screens.profile.CreatorScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.profile.SettingsScreen
import com.example.ui.screens.reference.ReferenceScreen
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DeepNavyBg
import com.example.ui.theme.LearnReactTheme
import com.example.ui.theme.ReactCyan

enum class MainTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    LEARN("Learn", Icons.Default.School),
    CODE("Code Lab", Icons.Default.Code),
    PRACTICE("Practice", Icons.Default.FitnessCenter),
    PROFILE("Profile", Icons.Default.Person)
}

sealed class AppDestination {
    data object MainTabs : AppDestination()
    data class LessonDetail(val lessonId: String) : AppDestination()
    data object Creator : AppDestination()
    data object Certificate : AppDestination()
    data object Interview : AppDestination()
    data object Settings : AppDestination()
    data object AiTutor : AppDestination()
    data object Reference : AppDestination()
}

class MainActivity : ComponentActivity() {

    private lateinit var userDataStore: UserDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        userDataStore = UserDataStore(this)

        setContent {
            LearnReactTheme(darkTheme = true) {
                MainAppRoot(userDataStore = userDataStore)
            }
        }
    }
}

@Composable
fun MainAppRoot(userDataStore: UserDataStore) {
    val userProfile by userDataStore.userProfile.collectAsState()

    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    var currentDestination by remember { mutableStateOf<AppDestination>(AppDestination.MainTabs) }
    var initialCodeForPlayground by remember { mutableStateOf<String?>(null) }

    // Onboarding Gate
    if (!userProfile.isOnboarded) {
        OnboardingScreen(
            onOnboardingFinished = { name, progExp, reactExp, goals, dailyGoal ->
                userDataStore.completeOnboarding(name, progExp, reactExp, goals, dailyGoal)
            }
        )
        return
    }

    // Handle Back Press on Sub-screens
    BackHandler(enabled = currentDestination != AppDestination.MainTabs) {
        currentDestination = AppDestination.MainTabs
    }

    // Handle Back Press on Tabs (switch to Home before exit)
    BackHandler(enabled = currentDestination == AppDestination.MainTabs && currentTab != MainTab.HOME) {
        currentTab = MainTab.HOME
    }

    when (val dest = currentDestination) {
        is AppDestination.LessonDetail -> {
            val lesson = CurriculumData.getLesson(dest.lessonId)
            if (lesson != null) {
                LessonDetailScreen(
                    lesson = lesson,
                    isCompleted = userProfile.completedLessonIds.contains(lesson.id),
                    isBookmarked = userProfile.bookmarkedIds.contains(lesson.id),
                    onBack = { currentDestination = AppDestination.MainTabs },
                    onToggleBookmark = { userDataStore.toggleBookmark(lesson.id) },
                    onMarkCompleted = { userDataStore.markLessonCompleted(lesson.id) },
                    onOpenInCodeLab = { code ->
                        initialCodeForPlayground = code
                        currentTab = MainTab.CODE
                        currentDestination = AppDestination.MainTabs
                    }
                )
            } else {
                currentDestination = AppDestination.MainTabs
            }
        }
        is AppDestination.Creator -> {
            CreatorScreen(onBack = { currentDestination = AppDestination.MainTabs })
        }
        is AppDestination.Certificate -> {
            CertificateScreen(
                userName = userProfile.name,
                onBack = { currentDestination = AppDestination.MainTabs }
            )
        }
        is AppDestination.Interview -> {
            InterviewScreen(onBack = { currentDestination = AppDestination.MainTabs })
        }
        is AppDestination.Settings -> {
            SettingsScreen(
                currentName = userProfile.name,
                currentDailyGoal = userProfile.dailyGoalMinutes,
                onSaveProfile = { newName, newGoal ->
                    userDataStore.completeOnboarding(
                        name = newName,
                        progExp = userProfile.programmingExperience,
                        reactExp = userProfile.reactExperience,
                        goals = userProfile.goals,
                        dailyGoal = newGoal
                    )
                    currentDestination = AppDestination.MainTabs
                },
                onResetProgress = {
                    userDataStore.resetProgress()
                    currentDestination = AppDestination.MainTabs
                },
                onBack = { currentDestination = AppDestination.MainTabs }
            )
        }
        is AppDestination.AiTutor -> {
            AiTutorScreen()
        }
        is AppDestination.Reference -> {
            ReferenceScreen()
        }
        is AppDestination.MainTabs -> {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DeepNavyBg),
                bottomBar = {
                    NavigationBar(
                        containerColor = DarkSurface,
                        contentColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .testTag("main_navigation_bar")
                    ) {
                        MainTab.entries.forEach { tab ->
                            val isSelected = currentTab == tab
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentTab = tab },
                                icon = {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.label,
                                        tint = if (isSelected) ReactCyan else Color(0xFF94A3B8),
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) ReactCyan else Color(0xFF94A3B8)
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = ReactCyan.copy(alpha = 0.2f)
                                ),
                                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentTab) {
                        MainTab.HOME -> HomeScreen(
                            userProfile = userProfile,
                            onNavigateToLearn = { currentTab = MainTab.LEARN },
                            onNavigateToCodeLab = { currentTab = MainTab.CODE },
                            onNavigateToPractice = { currentTab = MainTab.PRACTICE },
                            onNavigateToProjects = { currentTab = MainTab.PRACTICE },
                            onNavigateToReference = { currentDestination = AppDestination.Reference },
                            onNavigateToAiTutor = { currentDestination = AppDestination.AiTutor },
                            onNavigateToLesson = { lessonId ->
                                currentDestination = AppDestination.LessonDetail(lessonId)
                            }
                        )
                        MainTab.LEARN -> CurriculumScreen(
                            completedLessonIds = userProfile.completedLessonIds,
                            onSelectLesson = { lessonId ->
                                currentDestination = AppDestination.LessonDetail(lessonId)
                            }
                        )
                        MainTab.CODE -> PlaygroundScreen(
                            initialCode = initialCodeForPlayground
                        )
                        MainTab.PRACTICE -> PracticeScreen(
                            completedChallengeIds = userProfile.completedChallengeIds,
                            onMarkCompleted = { challengeId ->
                                userDataStore.markChallengeCompleted(challengeId)
                            }
                        )
                        MainTab.PROFILE -> ProfileScreen(
                            userProfile = userProfile,
                            onNavigateToCreator = { currentDestination = AppDestination.Creator },
                            onNavigateToCertificate = { currentDestination = AppDestination.Certificate },
                            onNavigateToSettings = { currentDestination = AppDestination.Settings },
                            onNavigateToInterview = { currentDestination = AppDestination.Interview }
                        )
                    }
                }
            }
        }
    }
}
