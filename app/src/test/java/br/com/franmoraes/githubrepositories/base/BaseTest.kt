package br.com.franmoraes.githubrepositories.base

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.AutoCloseKoinTest
import java.io.File

abstract class BaseTest : AutoCloseKoinTest() {
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun doBefore() {
        configureMockServer()
        TestSetup.start(getMockUrl())
    }

    @After
    open fun doOnFinish() {
        stopMockServer()
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    private fun getMockUrl() = mockServer.url("/").toString()


    fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
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