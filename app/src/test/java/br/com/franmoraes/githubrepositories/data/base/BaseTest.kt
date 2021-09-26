package br.com.franmoraes.githubrepositories.data.base

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

abstract class BaseTest : KoinTest {
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
}