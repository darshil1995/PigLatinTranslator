package com.piglatin.translator

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.piglatin.translator.view.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PigTranslatorTest {
    @get:Rule
    val activityScenario = ActivityScenario.launch(MainActivity::class.java)

    /**
     *  For Test Scenario Where the Textview is Displayed
     *  if there is any text in the EditText when translate button is clicked.
     *  It also checks if the translation is correct for "Hello" word to "ellohay"
     */
    @Test
    fun checkTranslation() {
        onView(withId(R.id.edit_message))
            .perform(clearText(), typeText("Hello"))
        //Delaying the button click
        runBlocking {
            launch {
                //Button Click
                onView(withId(R.id.btnTranslate)).perform(click())
                onView(withId(R.id.textViewTranslated))
                    .check(matches(isDisplayed())).check(matches(withText("ellohay")))
            }
            delay(5000)
        }
    }

    /**
     *  For Test Scenario Where the Textview is Displayed
     *  if there is any text in the EditText when translate button is clicked.
     *  It also checks if the translation is correct for first letter vowel like "Oh" word to "ohway"
     */
    @Test
    fun checkVowelTranslation() {
        onView(withId(R.id.edit_message))
            .perform(clearText(), typeText("Oh"))
        //Delaying the button click
        runBlocking {
            launch {
                //Button Click
                onView(withId(R.id.btnTranslate)).perform(click())
                onView(withId(R.id.textViewTranslated))
                    .check(matches(isDisplayed())).check(matches(withText("ohway")))
            }
            delay(5000)
        }
    }

    /**
     *  For Test Scenario Where the Textview is Displayed
     *  if there is NO text in the EditText when translate button is clicked
     */
    @Test
    fun checkNoTextTranslation() {
        onView(withId(R.id.edit_message))
            .perform(clearText(), typeText(""))
        //Delaying the button click
        runBlocking {
            launch {
                //Button Click
                onView(withId(R.id.btnTranslate)).perform(click())
                onView(withId(R.id.textViewTranslated))
                    .check(matches(isDisplayed())).check(matches(withText("")))
            }
            delay(5000)
        }
    }

}