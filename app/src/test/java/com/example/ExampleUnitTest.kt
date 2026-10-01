package com.example

import com.example.data.repository.ChallengeData
import com.example.data.repository.CurriculumData
import com.example.data.repository.ProjectData
import com.example.data.repository.ReferenceData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `curriculum repository contains 31 comprehensive levels`() {
        assertEquals(31, CurriculumData.allLevels.size)
        val level1 = CurriculumData.getLevel(1)
        assertNotNull(level1)
        assertEquals("Web Development Foundation", level1?.title)

        val level12 = CurriculumData.getLevel(12)
        assertNotNull(level12)
        assertEquals("React Hooks Overview & Rules", level12?.title)
    }

    @Test
    fun `challenge and project repositories contain rich problem sets`() {
        assertTrue(ChallengeData.allChallenges.isNotEmpty())
        assertTrue(ProjectData.allProjects.isNotEmpty())
        assertTrue(ReferenceData.allHooks.isNotEmpty())
    }
}
