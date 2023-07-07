package com.submission.dicodingstory.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.submission.dicodingstory.JsonConverter
import com.submission.dicodingstory.googlemaps.GoogleMapsFragment
import com.submission.dicodingstory.launchFragmentInHiltContainer
import com.submission.dicodingstory.util.Default.BASEURL_MOCK
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.submission.dicodingstory.R

@MediumTest
@HiltAndroidTest
class GoogleMapsFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private val mockWebServer = MockWebServer()



    @Before
    fun initialize()
    {
        mockWebServer
            .start(8080)
        BASEURL_MOCK = "http://127.0.0.1:808"
    }


    @After
    fun cleanup()
    {
        mockWebServer
            .shutdown()
    }

    @Test
    fun testLaunchGoogleMapsFragment_Success() {
        launchFragmentInHiltContainer<GoogleMapsFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("test_resp_success.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.googleMaps)).check(matches(isDisplayed()))
    }
}