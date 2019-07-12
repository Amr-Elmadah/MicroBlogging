package com.tasks.microblogging.ui.authordetails.presentation.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tasks.microblogging.R
import com.tasks.microblogging.base.presentation.view.adapter.BaseRecyclerAdapter
import com.tasks.microblogging.base.presentation.view.extension.setVisible
import com.tasks.microblogging.base.presentation.view.extension.showSnack
import com.tasks.microblogging.base.presentation.viewmodel.ViewModelFactory
import com.tasks.microblogging.data.remote.network.response.Author
import com.tasks.microblogging.data.remote.network.response.Post
import com.tasks.microblogging.data.remote.network.response.PostsParams
import com.tasks.microblogging.databinding.ActivityAuthorDetailsBinding
import com.tasks.microblogging.ui.authordetails.presentation.view.adapter.PostsAdapter
import com.tasks.microblogging.ui.authordetails.presentation.viewmodel.AuthorDetailsViewModel
import com.tasks.microblogging.util.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_author_details.*
import javax.inject.Inject

class AuthorDetailsActivity : AppCompatActivity(), BaseRecyclerAdapter.OnLoadMoreListener {

    companion object {
        const val EXTRA_AUTHOR = "author"
    }

    lateinit var activityAuthorDetailsBinding: ActivityAuthorDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AuthorDetailsViewModel>

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthorDetailsViewModel::class.java)
    }

    @Inject
    lateinit var manager: LinearLayoutManager

    @Inject
    lateinit var adapter: PostsAdapter

    private var page = 1
    private val limit = Constants.PAGE_SIZE
    private val sort = "date"
    private val order = "desc"
    private var isLastPage = false
    private var mPosts = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        activityAuthorDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_author_details)
        bindAuthor()
        setupControllers()
        getAuthorPosts()
    }

    private fun bindAuthor() {
        val extras = intent.extras
        extras?.let {
            activityAuthorDetailsBinding.author = (it.getParcelable(EXTRA_AUTHOR) as? Author)!!
        }
    }

    private fun setupControllers() {
        setupRecyclerView()
        observePostsChange()
    }

    private fun getAuthorPosts() {
        srlPosts.isRefreshing = true
        mViewModel.getAuthorPosts(
            PostsParams(
                authorId = activityAuthorDetailsBinding.author!!.authorID,
                page = page,
                limit = limit,
                sort = sort,
                order = order
            )
        )
    }

    private fun setupRecyclerView() {
        manager.orientation = RecyclerView.VERTICAL
        rvPosts.layoutManager = manager
        adapter.addOnLoadMoreListener(this, rvPosts, manager)
        rvPosts.adapter = adapter
        srlPosts.setOnRefreshListener {
            page = 1
            getAuthorPosts()
        }
    }

    override fun onLoadMore() {
        if (!srlPosts.isRefreshing && !isLastPage) {
            getAuthorPosts()
        }
    }

    @SuppressLint("CheckResult")
    private fun observePostsChange() {
        mViewModel.mPosts.observe(this, Observer { posts ->
            posts?.let {
                fillData(it)
            }
        })
        mViewModel.mPostsObservable.observe(this,
            successObserver = Observer {
                it?.let {
                    srlPosts.isRefreshing = false
                    llMainContent.showSnack(it)
                }
            }, commonErrorObserver = Observer {
                srlPosts.isRefreshing = false
            }, loadingObserver = Observer {
                it?.let {
                }
            }, networkErrorConsumer = Observer {
                srlPosts.isRefreshing = false
                llMainContent.showSnack(
                    getString(R.string.internet_connection),
                    Snackbar.LENGTH_LONG
                )
            })

        adapter.getViewClickedObservable().subscribe {
            it?.let {
                openPostCommentsActivity(it)
            }
        }
    }

    private fun fillData(posts: List<Post>) {
        if (page == 1) {
            mPosts = ArrayList(posts)
            adapter.replaceAllItems(mPosts.toMutableList())
        } else {
            mPosts.addAll(posts)
            adapter.addMoreItems(mPosts.toMutableList())
        }

        isLastPage = mPosts.size < limit || posts.isEmpty()

        if (!isLastPage) {
            page++
        }

        srlPosts.isRefreshing = false
        rvPosts.setVisible(mPosts.isNotEmpty())
        llNoData.setVisible(mPosts.isEmpty())
    }

    private fun openPostCommentsActivity(post: Post) {
        //TODO: If required to open details activity of the clicked author
        llMainContent.showSnack(post.title)
    }
}
