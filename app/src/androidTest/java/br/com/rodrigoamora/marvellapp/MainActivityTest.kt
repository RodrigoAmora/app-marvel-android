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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var context: Context

    @Before
    fun init() {
        ActivityScenario.launch(MainActivity::class.java)
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testShouldShowAppName() {
        Espresso
            .onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.withText(context.getString(R.string.app_name))))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testShouldShowListCharacters() {
        Espresso
            .onView(ViewMatchers.withId(R.id.list_characters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(RecyclerViewMatcher.recyclerViewWithId(R.id.list_characters).viewHolderViewAtPosition(0, R.id.tv_name_character_value))
            .check(ViewAssertions.matches(ViewMatchers.withText("3-D Man")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testClickOnItemRecyclerViewAndCheckNameCharacterInCharacterFragment() {
        Espresso
            .onView(RecyclerViewMatcher.recyclerViewWithId(R.id.list_characters).itemViewAtIndex(0))
            .perform(ViewActions.click())

        Espresso
            .onView(ViewMatchers.withId(R.id.tv_name_character))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(ViewMatchers.withId(R.id.tv_name_character))
            .check(ViewAssertions.matches(ViewMatchers.withText("3-D Man")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}