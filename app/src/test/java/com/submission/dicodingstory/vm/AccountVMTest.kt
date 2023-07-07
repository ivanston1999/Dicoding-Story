import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.repo.AppRepo
import com.submission.dicodingstory.repotest.CorouTR
import com.submission.dicodingstory.repotest.DataDummy
import com.submission.dicodingstory.repotest.getOrAwaitValue
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.vm.AccountVM
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AccountVMTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var corouTR = CorouTR()

    private lateinit var viewModel: AccountVM
    private val signInResponse = DataDummy.generateSignInResponse()

    private val mockRepo: AppRepo = Mockito.mock(AppRepo::class.java)

    @Before
    fun setup() {
        viewModel = AccountVM(mockRepo)
    }

    @Test
    fun `User signIn - error`() = runTest {
        val dataAcc: Flow<DataResult<AccountResponse>> = flowOf(DataResult.error("UNKNOWN ERROR", null))

        val req = AccountReq("email@mail.com", "password")
        Mockito.`when`(mockRepo.signIn(req)).thenReturn(dataAcc)

        val actual = viewModel.signIn(req).getOrAwaitValue()

        Assert.assertNotNull(actual)
        Assert.assertEquals(DataResult.error("UNKNOWN ERROR", null), actual)
    }

    @Test
    fun `User signIn - success`() = runTest {
        val expecResp = signInResponse
        val dataAcc: Flow<DataResult<AccountResponse>> = flowOf(DataResult.success(expecResp))

        val req = AccountReq("email@mail.com", "password")
        Mockito.`when`(mockRepo.signIn(req)).thenReturn(dataAcc)

        val actual = viewModel.signIn(req).getOrAwaitValue()

        Assert.assertNotNull(actual)
        Assert.assertEquals(actual.data, expecResp)
    }

    @Test
    fun `User signUp - error`() = runTest {
        val req = SignupReq("name", "email@mail.com", "password")
        val dataAcc: Flow<DataResult<BaseResponse>> = flowOf(DataResult.error("UNKNOWN ERROR", null))

        Mockito.`when`(mockRepo.signUp(req)).thenReturn(dataAcc)

        val actual = viewModel.signUp(req).getOrAwaitValue()

        Assert.assertNotNull(actual)
        Assert.assertEquals(DataResult.error("UNKNOWN ERROR", null), actual)
    }

    @Test
    fun `User signUp - success`() = runTest {
        val expecResp = DataDummy.generateSignUpResponse()
        val req = SignupReq("name", "email@mail.com", "password")
        val dataAcc: Flow<DataResult<BaseResponse>> = flowOf(DataResult.success(expecResp))

        Mockito.`when`(mockRepo.signUp(req)).thenReturn(dataAcc)

        val actual = viewModel.signUp(req).getOrAwaitValue()

        Assert.assertNotNull(actual)
        Assert.assertEquals(actual.data, expecResp)
    }
}
