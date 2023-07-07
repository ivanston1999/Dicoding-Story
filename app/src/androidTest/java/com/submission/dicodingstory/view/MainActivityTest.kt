package com.submission.dicodingstory.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.submission.dicodingstory.EspressoIdlingResource
import com.submission.dicodingstory.main.SplashScreenActivity
import com.submission.dicodingstory.R
import com.submission.dicodingstory.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule =
        HiltAndroidRule(this)
    @get:Rule
    var activity =
        ActivityScenarioRule(SplashScreenActivity::class.java
        )

    @Before
    fun initialize()
    {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        Intents.init()}

    @After
    fun cleanup()
    {
        IdlingRegistry
            .getInstance()
            .unregister(EspressoIdlingResource.countingIdlingResource)
        Intents
            .release()}

    @Test
    fun testLoadDetailInformation()
    {
        intended(hasComponent(MainActivity::class.java.name))
        onView(withId(R.id.rvStory))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvStory))
            .perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))



        onView(withId(R.id.btnBack))
            .perform(click())


        onView(withId(R.id.googleMapsFragment))
            .perform(click())


        onView(withId(R.id.newStoryFragment))
            .perform(click())


        onView(withId(R.id.btnBack))
            .perform(click())


        onView(withId(R.id.dashboardFragment))
            .perform(click())}




}