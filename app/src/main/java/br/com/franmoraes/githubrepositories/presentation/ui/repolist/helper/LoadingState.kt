package br.com.franmoraes.githubrepositories.presentation.ui.repolist.helper

data class LoadingState private constructor(val status: Status, val msg: Error? = null) {
    companion object {
        val FINISHED = LoadingState(Status.SUBMISSION_SUCCESS)
        val IN_PROGRESS = LoadingState(Status.SUBMISSION_IN_PROGRESS)
        fun error(error: Error?) = LoadingState(Status.SUBMISSION_FAILURE, error)
    }

    enum class Status {
        SUBMISSION_IN_PROGRESS,
        SUBMISSION_SUCCESS,
        SUBMISSION_FAILURE
    }

    enum class Error {
        GENERIC_ERROR,
        CONNECTION,
        EMPTY_LIST
    }
}