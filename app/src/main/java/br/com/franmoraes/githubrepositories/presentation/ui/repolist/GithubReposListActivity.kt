package br.com.franmoraes.githubrepositories.presentation.ui.repolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.franmoraes.githubrepositories.R
import br.com.franmoraes.githubrepositories.databinding.GithubListActivityBinding
import br.com.franmoraes.githubrepositories.presentation.extensions.hide
import br.com.franmoraes.githubrepositories.presentation.extensions.show
import br.com.franmoraes.githubrepositories.presentation.ui.repolist.adapter.ReposAdapter
import br.com.franmoraes.githubrepositories.presentation.ui.repolist.custom.RecyclerViewPagination
import br.com.franmoraes.githubrepositories.presentation.ui.repolist.helper.LoadingState
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO
import org.koin.androidx.viewmodel.ext.android.viewModel

class GithubReposListActivity : AppCompatActivity() {

    private val viewModel: GithubReposListViewModel by viewModel()
    private lateinit var repoListBinding: GithubListActivityBinding
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repoListBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_github_repo_list
        )
        repoListBinding.lifecycleOwner = this@GithubReposListActivity

        prepareObservers()
        initViews()
    }

    private fun prepareObservers() {
        viewModel.getGithubRepositories().observe(
            this, Observer {
                if (it.isEmpty()) {
                    showEmptyView()
                } else {
                    initReposRecyclerView(it)
                }
            }
        )

        viewModel.getLoadingSate().observe(
            this, Observer {
                when (it.status) {
                    LoadingState.Status.SUBMISSION_IN_PROGRESS -> {
                        repoListBinding.viewLoaderLayout.rootView.show()
                    }
                    LoadingState.Status.SUBMISSION_SUCCESS -> {
                        repoListBinding.viewLoaderLayout.rootView.hide()
                    }
                    else -> {
                       showEmptyView()
                    }
                }
            }
        )
    }

    private fun showEmptyView() {
        repoListBinding.viewLoaderLayout.rootView.hide()
        repoListBinding.emptyContentLayout.emptyContainer.show()
    }

    private fun initViews() {
        setSupportActionBar(findViewById(R.id.appToolbar))
        repoListBinding.backToTopFAB.setOnClickListener {
            repoListBinding.repoList.smoothScrollToPosition(0)
        }
    }

    private fun initReposRecyclerView(reposList: List<GithubRepositoriesVO>) {
        if (::reposAdapter.isInitialized) {
            reposAdapter.updateList(reposList)
        } else {
            reposAdapter = ReposAdapter(reposList)
            var listLayoutManager =
                LinearLayoutManager(this@GithubReposListActivity, RecyclerView.VERTICAL, false)
            repoListBinding.repoList.apply {
                adapter = reposAdapter
                layoutManager = listLayoutManager

                addOnScrollListener(object : RecyclerViewPagination(this, listLayoutManager) {
                    override fun fetchMore() {
                        viewModel.fetchRepositories()
                    }

                    override fun isLastPage(): Boolean = viewModel.isLastPage()

                    override fun actionHideFAB() {
                        repoListBinding.backToTopFAB.hide()
                    }

                    override fun actionShowFAB() {
                       repoListBinding.backToTopFAB.show()
                    }
                })
            }
        }
    }

}