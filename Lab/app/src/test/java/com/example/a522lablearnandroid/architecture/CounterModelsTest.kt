package com.example.a522lablearnandroid.architecture

import com.example.a522lablearnandroid.architecture.mvc.MvcCounterModel
import com.example.a522lablearnandroid.architecture.mvp.MvpCounterModel
import com.example.a522lablearnandroid.architecture.mvvm.MvvmCounterModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CounterModelsTest {

    @Test
    fun mvcCounter_initialValue_isZero() {
        val model = MvcCounterModel()
        assertEquals(0, model.getCount())
    }

    @Test
    fun mvcCounter_incrementOnce_updatesToOne() {
        val model = MvcCounterModel()
        model.incrementCounter()
        assertEquals(1, model.getCount())
    }

    @Test
    fun mvcCounter_incrementMultipleTimes_updatesCorrectly() {
        val model = MvcCounterModel()
        repeat(5) { model.incrementCounter() }
        assertEquals(5, model.getCount())
    }

    @Test
    fun mvpCounter_initialValue_isZero() {
        val model = MvpCounterModel()
        assertEquals(0, model.getCount())
    }

    @Test
    fun mvpCounter_incrementTwice_updatesToTwo() {
        val model = MvpCounterModel()
        model.incrementCounter()
        model.incrementCounter()
        assertEquals(2, model.getCount())
    }

    @Test
    fun mvvmCounter_initialValue_isZero() {
        val model = MvvmCounterModel()
        assertEquals(0, model.getCount())
    }

    @Test
    fun mvvmCounter_incrementThenDecrement_backToZero() {
        val model = MvvmCounterModel()
        model.incrementCounter()
        model.DecrementCounter()
        assertEquals(0, model.getCount())
    }

    @Test
    fun mvvmCounter_decrementFromZero_goesNegativeOne() {
        val model = MvvmCounterModel()
        model.DecrementCounter()
        assertEquals(-1, model.getCount())
    }
}

