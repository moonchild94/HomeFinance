package ru.divizdev.homefinance.ui

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.presentation.main.view.MainActivity
import ru.divizdev.homefinance.presentation.operationslist.view.OperationListFragment

@RunWith(AndroidJUnit4::class)
class OperationsUiTest {
    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityTestRule.activity.openOperations()
    }

    @Test
    fun newOperation_add_showsListWithNewOperation() {
        onView(withId(R.id.add_operation)).check(matches(isDisplayed()))
        onView(withId(R.id.add_operation)).perform(click())

        val startPosition = getLastPositionInRecycle(R.id.list_transaction_recylcer_view)

        selectSpinnerItem(R.id.type_category_spinner, "Расход")
        selectSpinnerItem(R.id.category_spinner, "Еда")
        selectSpinnerItem(R.id.wallet_spinner, "Кошелек2")
        typeTextInEditText(R.id.value_edit_text, "100")
        selectSpinnerItem(R.id.currency_spinner, "\u0024")
        Espresso.closeSoftKeyboard()
        typeTextInEditText(R.id.comment_edit_text, "Обед")
        onView(withId(R.id.save_button)).perform(click())

        val endPosition = getLastPositionInRecycle(R.id.list_transaction_recylcer_view)

        Assert.assertEquals(startPosition + 1, endPosition)
        onView(allOf(withId(R.id.list_transaction_recylcer_view), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(endPosition))
        onView(withId(R.id.list_transaction_recylcer_view))
                .check(matches(hasDescendant(withText("Обед"))))
    }

    private fun getLastPositionInRecycle(recycleId: Int): Int {
        return (activityTestRule.activity.supportFragmentManager
                .fragments.last() as OperationListFragment)
                .view?.findViewById<RecyclerView>(recycleId)?.adapter?.itemCount!! - 1
    }

    private fun selectSpinnerItem(viewId: Int, itemTitle: String) {
        onView(withId(viewId)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(itemTitle))).perform(click())
    }

    private fun typeTextInEditText(viewId: Int, text: String) {
        onView(withId(viewId)).perform(replaceText(text))
        onView(withId(viewId))
                .check(matches(withText(containsString(text))))
    }
}