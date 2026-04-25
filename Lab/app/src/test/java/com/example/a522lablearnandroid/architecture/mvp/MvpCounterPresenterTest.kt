package com.example.a522lablearnandroid.architecture.mvp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MvpCounterPresenterTest {

    @Test
    fun onIncrementClicked_updatesViewWithOne() {
        val view = FakeCounterView()
        val model = MvpCounterModel()
        val presenter = MvpCounterPresenter(view, model)

        presenter.onIncrementClicked()

        assertEquals(1, view.lastShownCount)
    }

    @Test
    fun onIncrementClicked_twice_updatesViewWithTwo() {
        val view = FakeCounterView()
        val model = MvpCounterModel()
        val presenter = MvpCounterPresenter(view, model)

        presenter.onIncrementClicked()
        presenter.onIncrementClicked()

        assertEquals(2, view.lastShownCount)
    }

    @Test
    fun beforeAnyClick_viewHasNoCount() {
        val view = FakeCounterView()
        assertNull(view.lastShownCount)
    }

    private class FakeCounterView : MvpCounterView {
        var lastShownCount: Int? = null

        override fun showCount(count: Int) {
            lastShownCount = count
        }
    }
}

