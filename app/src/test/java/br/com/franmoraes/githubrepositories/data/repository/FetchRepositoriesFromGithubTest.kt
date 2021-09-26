package br.com.franmoraes.githubrepositories.data.repository

import br.com.franmoraes.githubrepositories.data.base.BaseTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.inject
import retrofit2.HttpException
import java.io.File
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class FetchRepositoriesFromGithubTest : BaseTest() {

    private val fetchRepositoriesFromGithub by inject<IFetchRepositoriesFromGithub>()

    @Test
    fun `fetch github repositories with success`() {
        mockHttpResponse("repos_list.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            val repositories = fetchRepositoriesFromGithub.fetchRepositories(-1)
            assertEquals(30, repositories.size)
            assertEquals("square/okhttp", repositories.first().fullName)
            assertEquals("okhttp", repositories.first().name)
            assertEquals("square", repositories.first().owner.name)
            assertEquals(40851, repositories.first().watchers)
            assertEquals(8634, repositories.first().forks)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `fetch github repositories with invalid parameters`() {
        mockHttpResponse("repos_list_invalid_parameter.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            fetchRepositoriesFromGithub.fetchRepositories(-1)
        }
    }

    @Test(expected = HttpException::class)
    fun `fetch github repositories with max page limit`() {
        mockHttpResponse("repos_list.json", 422)

        runBlocking {
            fetchRepositoriesFromGithub.fetchRepositories(-1)
        }
    }

    private fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(jsonFileToString(fileName))
    )

    private fun jsonFileToString(path: String): String {
        val uri = javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}