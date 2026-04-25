package com.example.a522lablearnandroid.architecture.mvi

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MviStateAndIntentTest {

    @Test
    fun counterState_defaultCount_isZero() {
        val state = CounterState()
        assertEquals(0, state.count)
    }

    @Test
    fun counterState_copy_updatesOnlyCount() {
        val original = CounterState(count = 7)
        val changed = original.copy(count = 9)
        assertEquals(7, original.count)
        assertEquals(9, changed.count)
    }

    @Test
    fun incrementIntent_isCounterIntent() {
        val intent: CounterIntent = CounterIntent.IncrementCounter
        assertTrue(intent is CounterIntent)
    }

    @Test
    fun decrementIntent_isCounterIntent() {
        val intent: CounterIntent = CounterIntent.DecrementCounter
        assertTrue(intent is CounterIntent)
    }

    @Test
    fun incrementAndDecrementIntent_areDifferentTypes() {
        val inc = CounterIntent.IncrementCounter
        val dec = CounterIntent.DecrementCounter
        assertTrue(inc::class != dec::class)
    }
}

