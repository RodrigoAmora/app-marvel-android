package br.com.rodrigoamora.marvellapp

import android.content.Context
import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
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
class ListCharactersFragmentTest {

    private lateinit var context: Context

    @Before
    fun init() {
        ActivityScenario.launch(MainActivity::class.java)
        this.context = InstrumentationRegistry.getInstrumentation().targetContext
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
    fun testSearchCharacterByName() {
        Espresso
            .onView(ViewMatchers.withId(R.id.list_characters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(RecyclerViewMatcher.recyclerViewWithId(R.id.list_characters).viewHolderViewAtPosition(0, R.id.tv_name_character_value))
            .check(ViewAssertions.matches(ViewMatchers.withText("3-D Man")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(ViewMatchers.withId(R.id.fab_search_character_by_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Espresso
            .onView(ViewMatchers.withId(R.id.sv_search_character_by_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText("Hulk"))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER), closeSoftKeyboard())

        Thread.sleep(7000L)

        Espresso
            .onView(RecyclerViewMatcher.recyclerViewWithId(R.id.list_characters).viewHolderViewAtPosition(0, R.id.tv_name_character_value))
            .check(ViewAssertions.matches(ViewMatchers.withText("Hulk")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(RecyclerViewMatcher.recyclerViewWithId(R.id.list_characters).viewHolderViewAtPosition(1, R.id.tv_name_character_value))
            .check(ViewAssertions.matches(ViewMatchers.withText("Hulk (HAS)")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}