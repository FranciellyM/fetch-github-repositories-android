package br.com.franmoraes.githubrepositories.presentation.di

import br.com.franmoraes.githubrepositories.presentation.ui.repolist.GithubReposListViewModel
import br.com.franmoraes.githubrepositories.presentation.vo.mapper.GithubRepositoriesVOMapper
import br.com.franmoraes.githubrepositories.presentation.vo.mapper.RepositoryOwnerVOMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal object PresentationModule {
    val module = module {

        viewModel {
            GithubReposListViewModel(
                fetchRepositoriesFromGithub = get(),
                voMapper = get()
            )
        }

        factory {
            GithubRepositoriesVOMapper(ownerMapper = get())
        }

        factory {
            RepositoryOwnerVOMapper()
        }
    }
}