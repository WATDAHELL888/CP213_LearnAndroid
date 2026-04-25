package com.example.a522lablearnandroid.examples

import com.example.a522lablearnandroid.architecture.mvvm.MvvmCounterModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * This class intentionally contains failing tests for demonstration.
 */
class IntentionalFailingExamplesTest {

    @Test
    fun expectedToFail_wrongMathExpectation() {
        assertEquals(5, 2 + 2)
    }

    @Test
    fun expectedToFail_wrongCounterExpectation() {
        val model = MvvmCounterModel()
        model.incrementCounter()
        assertEquals(2, model.getCount())
    }
}

