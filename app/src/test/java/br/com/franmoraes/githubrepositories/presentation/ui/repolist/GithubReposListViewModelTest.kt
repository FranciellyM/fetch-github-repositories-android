package br.com.franmoraes.githubrepositories.presentation.ui.repolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.franmoraes.githubrepositories.base.CoroutineTestRule
import br.com.franmoraes.githubrepositories.data.remote.entity.Repositories
import br.com.franmoraes.githubrepositories.data.remote.entity.RepositoryOwner
import br.com.franmoraes.githubrepositories.data.repository.IFetchRepositoriesFromGithub
import br.com.franmoraes.githubrepositories.presentation.ui.repolist.helper.LoadingState
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO
import br.com.franmoraes.githubrepositories.presentation.vo.mapper.GithubRepositoriesVOMapper
import br.com.franmoraes.githubrepositories.presentation.vo.mapper.RepositoryOwnerVOMapper
import com.nhaarman.mockitokotlin2.doThrow
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GithubReposListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var repoListObserver: Observer<List<GithubRepositoriesVO>>

    @Mock
    private lateinit var loadingStateObserver: Observer<LoadingState>

    @Mock
    private lateinit var fetchRepositoriesFromGithub: IFetchRepositoriesFromGithub

    private lateinit var mapper: GithubRepositoriesVOMapper
    private lateinit var fetchReposViewModel: GithubReposListViewModel
    private val actualPage = 1L

    @Before
    fun doBefore() {
        mapper = GithubRepositoriesVOMapper(RepositoryOwnerVOMapper())
        fetchReposViewModel = GithubReposListViewModel(fetchRepositoriesFromGithub, mapper)

        fetchReposViewModel.getGithubRepositories().observeForever(repoListObserver)
        fetchReposViewModel.getLoadingSate().observeForever(loadingStateObserver)
    }

    @After
    fun doAfter() {
        fetchReposViewModel.getGithubRepositories().removeObserver(repoListObserver)
        fetchReposViewModel.getLoadingSate().removeObserver(loadingStateObserver)
    }

    @Test
    fun `given api response on the first page with valid data actualPage should be 1`() {
        coroutineTestRule.runTestBlock {
            //Given
            val mockRepositoriesList = makeRepositoriesListMock()
            val mockRepositoriesListVO = mockRepositoriesList.map { mapper.transform(it) }

            //When
            doReturn(mockRepositoriesList)
                .`when`(fetchRepositoriesFromGithub)
                .fetchRepositories(actualPage)
            fetchReposViewModel.fetchRepositories()

            //Then
            verify(fetchRepositoriesFromGithub).fetchRepositories(actualPage)
            verify(repoListObserver).onChanged(mockRepositoriesListVO)
            verify(loadingStateObserver).onChanged(LoadingState.FINISHED)
        }
    }

    @Test
    fun `given api response with end content status code`() {
        verifyResponseWithException(HttpException(
            Response.error<ResponseBody>(
                422,
                "Only the first 1000 search results are available".toResponseBody()
            )
        ),
            loadingState = LoadingState.FINISHED,
            completion = {
                verify(repoListObserver).onChanged(emptyList())
            })
    }

    @Test
    fun `given api response with and HttpException`() {
        verifyResponseWithException(
            HttpException(
                Response.error<ResponseBody>(
                    403,
                    "Forbidden".toResponseBody()
                )
            )
        )
    }

    @Test
    fun `given api response with invalid parameter`() {
        verifyResponseWithException(IllegalArgumentException())
    }

    @Test
    fun `given api response with any exception`() {
        verifyResponseWithException(RuntimeException())
    }

    private fun verifyResponseWithException(
        exception: Throwable,
        loadingState: LoadingState = LoadingState.error(LoadingState.Error.GENERIC_ERROR),
        completion: (() -> Unit)? = null
    ) {
        coroutineTestRule.runTestBlock {
            //When
            doThrow(exception)
                .`when`(fetchRepositoriesFromGithub)
                .fetchRepositories(actualPage)
            fetchReposViewModel.fetchRepositories()

            //Then
            verify(fetchRepositoriesFromGithub).fetchRepositories(actualPage)
            verify(loadingStateObserver).onChanged(loadingState)
            completion?.invoke()
        }
    }

    private fun makeRepositoriesListMock() = listOf(
        Repositories(
            repositoryId = 1,
            fullName = "FranicellyM/fetch-github-repositories-android",
            name = "fetch-github-repositories-android",
            owner = RepositoryOwner(
                avatar = "https://github.com/any_image.png",
                name = "FranciellyM"
            ),
            forks = 1,
            watchers = 1,
            description = "fetch-github-repositories-android"
        )
    )
}
