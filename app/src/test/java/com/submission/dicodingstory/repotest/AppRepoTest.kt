package com.submission.dicodingstory.repotest

import com.submission.dicodingstory.api.AccountApiService
import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.data.StoryData
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.error.NetworkHandler
import com.submission.dicodingstory.error.OperationStatus
import com.submission.dicodingstory.error.ResponseHandler
import com.submission.dicodingstory.repo.AppRepo
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AppRepoTest {

    @get:Rule
    var corouTR = CorouTR()
    @Mock
    private lateinit var src: StoryData
    @Mock
    private lateinit var srcMock: StoryData
    @Mock
    private lateinit var repoMock: AppRepo
    private val dummyDescription = DataDummy.generateReqBody()
    private val dummyMP = DataDummy.generateMultipartFile()
    private val signInReq = AccountReq("email@mail.com", "password")
    private val signInResponse = DataDummy.generateSignInResponse()
    private val signupReq = SignupReq("Name", "email@mail.com", "password")
    private val signupResponse = DataDummy.generateSignUpResponse()
    private val upResp = DataDummy.generateUpdateStory()

    @Before
    fun initialize()
    {
        val accountApiService = mock(AccountApiService::class.java)
        val storyApiService = mock(StoryApiService::class.java)
        val networkHandler = mock(NetworkHandler::class.java)
        val responseHandler = mock(ResponseHandler::class.java)
        src = StoryData(networkHandler, responseHandler, accountApiService, storyApiService)
        srcMock = mock(StoryData::class.java)
        repoMock = mock(AppRepo::class.java)}



    @Test
    fun `User signIn - success`() = runBlocking {
        val serv = DummyServ()
        val expecRes = signInResponse
        val actualResult = serv
            .signIn(signInReq)
            .body()
        Assert
            .assertNotNull(actualResult)
        Assert
            .assertEquals(expecRes, actualResult)}



    @Test
    fun `User signIn - error`() = runBlocking {
        val serv = DummyServ()
        val expec = DataDummy
            .generateSignInError()
        val actual = serv
            .signInError()
        Assert
            .assertNotNull(actual)
        Assert
            .assertEquals(expec, actual)
    }

    @Test
    fun `User signUp - success`() = runBlocking {
        val serv = DummyServ()
        val expecRes = signupResponse
        val actualResult = serv
            .signUp(signupReq)
            .body()
        Assert
            .assertNotNull(actualResult)
        Assert
            .assertEquals(expecRes, actualResult)}

    @Test
    fun `User signUp - error`() = runBlocking {
        val serv = DummyServ()
        val expec = DataDummy
            .generateSignUpError()
        val actual = serv
            .signUpError()
        Assert
            .assertNotNull(actual)
        Assert
            .assertEquals(expec, actual)
    }

    @Test
    fun `Get story with location - success`() = runBlocking {
        val expecRes = flowOf(
            DataResult
                .success(DataDummy
                    .generateStoryResponse()))
        `when`(repoMock
            .getStoryLoc())
            .thenReturn(expecRes)
        repoMock
            .getStoryLoc()
            .collect { result ->
            if (result
                    .status == OperationStatus.SUCCESS) {
                Assert
                    .assertNotNull(result)

                expecRes
                    .collect{
                    Assert.assertEquals(it, result)}}}
    }

    @Test
    fun `Get story with location - error`() = runBlocking {
        val expecRes = flowOf(DataResult
            .error("UNKNOWN ERROR", null))
        `when`(repoMock
            .getStoryLoc())
            .thenReturn(expecRes)
        repoMock
            .getStoryLoc()
            .collect { result ->
            if (result
                    .status == OperationStatus.ERROR) {
                Assert
                    .assertNotNull(result)
                expecRes.collect{
                    Assert.assertEquals(it, result)}}}}


    @Test
    fun `Upload new story - success`(): Unit = runBlocking {
        val serv = DummyServ()
        val expecRes = upResp
        val actualResult = serv
            .uploadStory(dummyDescription, dummyMP, null, null).body()
        Assert
            .assertNotNull(actualResult)
        Assert
            .assertEquals(expecRes, actualResult)}



    @Test
    fun `Upload new story - error`(): Unit = runBlocking {
        val serv = DummyServ()
        val expec = DataDummy
            .generateUploadError()
        val actual = serv
            .uploadError()
        Assert
            .assertNotNull(actual)
        Assert
            .assertEquals(expec, actual)
    }

}