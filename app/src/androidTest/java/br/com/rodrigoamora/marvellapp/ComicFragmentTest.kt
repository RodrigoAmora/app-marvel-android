package br.com.rodrigoamora.marvellapp

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rodrigoamora.marvellapp.ui.activity.MainActivity
import com.levibostian.recyclerviewmatcher.RecyclerViewMatcher
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicFragmentTest {

    private lateinit var context: Context

    @Before
    fun init() {
        ActivityScenario.launch(MainActivity::class.java)
        this.context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testClickOnItemRecyclerViewAndCheckNameCharacterInCharacterFragment() {
        Espresso
            .onView(RecyclerViewMatcher.recyclerViewWithId(R.id.list_characters).itemViewAtIndex(1))
            .perform(ViewActions.click())

        Espresso
            .onView(ViewMatchers.withId(R.id.tv_name_character))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(ViewMatchers.withId(R.id.sp_comics))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(7000L)

        Espresso
            .onView(ViewMatchers.withId(R.id.sp_comics))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Espresso
            .onData(CoreMatchers.anything())
            .atPosition(2)
            .perform(ViewActions.click())

        Espresso
            .onView(ViewMatchers.withId(R.id.tv_title_comic))
            .check(ViewAssertions.matches(ViewMatchers.withText("Hulk (2008) #55")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}