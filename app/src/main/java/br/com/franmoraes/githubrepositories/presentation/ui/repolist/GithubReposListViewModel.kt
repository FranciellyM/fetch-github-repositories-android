package br.com.franmoraes.githubrepositories.presentation.ui.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.franmoraes.githubrepositories.data.repository.IFetchRepositoriesFromGithub
import br.com.franmoraes.githubrepositories.presentation.ui.repolist.helper.LoadingState
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO
import br.com.franmoraes.githubrepositories.presentation.vo.mapper.GithubRepositoriesVOMapper
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class GithubReposListViewModel(
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

    fun fetchRepositories() {
        viewModelScope.launch {
            try {
                loading.postValue(LoadingState.IN_PROGRESS)

                val githubRepos = fetchRepositoriesFromGithub.fetchRepositories(++currentPage)
                val githubReposVO = githubRepos.map { repo -> voMapper.transform(repo) }

                reposList.addAll(githubReposVO)
                githubRepositoriesVO.postValue(reposList)

                loading.postValue(LoadingState.FINISHED)
            } catch (error: HttpException) {
                if(error.code() == END_OF_CONTENT) {
                    isLastPage = true
                    githubRepositoriesVO.postValue(emptyList())
                    loading.postValue(LoadingState.FINISHED)
                } else {
                    loading.postValue(LoadingState.error(LoadingState.Error.GENERIC_ERROR))
                }
            } catch (error: Exception) {
                loading.postValue(LoadingState.error(LoadingState.Error.GENERIC_ERROR))
            }
        }
    }

    fun isLastPage(): Boolean = isLastPage

    companion object {
        private const val END_OF_CONTENT = 422
    }
}