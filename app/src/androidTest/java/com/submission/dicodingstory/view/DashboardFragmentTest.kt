package com.submission.dicodingstory.view

import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.submission.dicodingstory.EspressoIdlingResource
import com.submission.dicodingstory.JsonConverter
import com.submission.dicodingstory.dashboard.DashboardFragment
import com.submission.dicodingstory.launchFragmentInHiltContainer
import com.submission.dicodingstory.util.Default.BASEURL_MOCK
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.submission.dicodingstory.R


@MediumTest
@ExperimentalPagingApi
@HiltAndroidTest
class DashboardFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private val mockWebServer = MockWebServer()




    @Before
    fun initialize()
    {
        mockWebServer.start(8080)
        BASEURL_MOCK = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(  EspressoIdlingResource.countingIdlingResource)}






    @After
    fun cleanup()
    {
        mockWebServer
            .shutdown()
        IdlingRegistry
            .getInstance()
            .unregister(EspressoIdlingResource
            .countingIdlingResource)}







    @Test
    fun testLaunchDashboardFragment_Success()
    {
        launchFragmentInHiltContainer<DashboardFragment>()
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("test_resp_success.json"))
        mockWebServer
            .enqueue(mockResponse)
        onView(withId(R.id.rvStory)).check(matches(isDisplayed()))}










    @Test
    fun testLaunchHomeFragment_Failed()
    {
        launchFragmentInHiltContainer<DashboardFragment>()
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody(JsonConverter.readStringFromFile("test_resp_empty.json"))
        mockWebServer
            .enqueue(mockResponse)
        onView(withId(R.id.failed_dashboard)).check(matches(not(isDisplayed())))}








    @Test
    fun testLauncherDashboardFragment_Empty()
    {
        launchFragmentInHiltContainer<DashboardFragment>()
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("test_resp_empty.json"))
        mockWebServer
            .enqueue(mockResponse)
        onView(withId(R.id.failed_dashboard)).check(matches(not(isDisplayed())))}

}
