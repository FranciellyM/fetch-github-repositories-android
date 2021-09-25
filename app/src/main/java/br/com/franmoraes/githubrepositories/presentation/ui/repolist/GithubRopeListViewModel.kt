package br.com.franmoraes.githubrepositories.presentation.ui.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.franmoraes.githubrepositories.data.repository.IFetchRepositoriesFromGithub
import br.com.franmoraes.githubrepositories.presentation.ui.repolist.helper.LoadingState
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO
import br.com.franmoraes.githubrepositories.presentation.vo.mapper.GithubRepositoriesVOMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class GithubRopeListViewModel(
    private val fetchRepositoriesFromGithub: IFetchRepositoriesFromGithub,
    private val voMapper: GithubRepositoriesVOMapper
) : ViewModel() {
    private val loading = MutableLiveData<LoadingState>()
    fun getLoadingSate(): LiveData<LoadingState> = loading

    private val githubRepositoriesVO = MutableLiveData<List<GithubRepositoriesVO>>()
    fun getGithubRepositories(): LiveData<List<GithubRepositoriesVO>> = githubRepositoriesVO

    private var currentPage: Long = 0
    private var isLastPage: Boolean = false
    private var reposList = mutableListOf<GithubRepositoriesVO>()

    init {
        fetchRepositories()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            try {
                loading.postValue(LoadingState.IN_PROGRESS)

                val githubRepos = fetchRepositoriesFromGithub.fetchRepositories(++currentPage)
                val githubReposVO = githubRepos.map { repo -> voMapper.transform(repo) }

                reposList.addAll(githubReposVO)

                githubRepositoriesVO.postValue(reposList)

                loading.postValue(LoadingState.FINISHED)
            } catch (error: Exception) {
                when (error) {
                    is HttpException -> {
                        if(error.code() == 422) {
                            isLastPage = true
                            githubRepositoriesVO.postValue(emptyList())
                        } else {
                            loading.postValue(LoadingState.error(LoadingState.Error.GENERIC_ERROR))
                        }
                    }
                    else -> {
                        loading.postValue(LoadingState.error(LoadingState.Error.GENERIC_ERROR))
                    }
                }
            }
        }
    }

    fun isLastPage(): Boolean = isLastPage
}