package com.tasks.microblogging.ui.postcomments.presentation.view.activity

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
import com.tasks.microblogging.data.remote.network.response.Comment
import com.tasks.microblogging.data.remote.network.response.CommentsParams
import com.tasks.microblogging.data.remote.network.response.Post
import com.tasks.microblogging.databinding.ActivityPostCommentsBinding
import com.tasks.microblogging.ui.postcomments.presentation.view.adapter.CommentsAdapter
import com.tasks.microblogging.ui.postcomments.presentation.viewmodel.PostCommentsViewModel
import com.tasks.microblogging.util.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_post_comments.*
import javax.inject.Inject

class PostCommentsActivity : AppCompatActivity(), BaseRecyclerAdapter.OnLoadMoreListener {

    companion object {
        const val EXTRA_POST = "post"
    }

    lateinit var activityPostCommentsBinding: ActivityPostCommentsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<PostCommentsViewModel>

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PostCommentsViewModel::class.java)
    }

    @Inject
    lateinit var manager: LinearLayoutManager

    @Inject
    lateinit var adapter: CommentsAdapter

    private var page = 1
    private val limit = Constants.PAGE_SIZE
    private val sort = "date"
    private val order = "desc"
    private var isLastPage = false
    private var mComments = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        activityPostCommentsBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_comments)
        bindPost()
        setupControllers()
        getPostComments()
    }

    private fun bindPost() {
        val extras = intent.extras
        extras?.let {
            activityPostCommentsBinding.post = (it.getParcelable(EXTRA_POST) as? Post)!!
        }
    }

    private fun setupControllers() {
        setupRecyclerView()
        observePostsChange()
    }

    private fun getPostComments() {
        srlComments.isRefreshing = true
        mViewModel.getPostComments(
            CommentsParams(
                postId = activityPostCommentsBinding.post!!.id,
                page = page,
                limit = limit,
                sort = sort,
                order = order
            )
        )
    }

    private fun setupRecyclerView() {
        manager.orientation = RecyclerView.VERTICAL
        rvComments.layoutManager = manager
        adapter.addOnLoadMoreListener(this, rvComments, manager)
        rvComments.adapter = adapter
        srlComments.setOnRefreshListener {
            page = 1
            getPostComments()
        }
    }

    override fun onLoadMore() {
        if (!srlComments.isRefreshing && !isLastPage) {
            getPostComments()
        }
    }

    @SuppressLint("CheckResult")
    private fun observePostsChange() {
        mViewModel.mComments.observe(this, Observer { comments ->
            comments?.let {
                fillData(it)
            }
        })
        mViewModel.mCommentsObservable.observe(this,
            successObserver = Observer {
                it?.let {
                    srlComments.isRefreshing = false
                    llMainContent.showSnack(it)
                }
            }, commonErrorObserver = Observer {
                srlComments.isRefreshing = false
            }, loadingObserver = Observer {
                it?.let {
                }
            }, networkErrorConsumer = Observer {
                srlComments.isRefreshing = false
                llMainContent.showSnack(
                    getString(R.string.internet_connection),
                    Snackbar.LENGTH_LONG
                )
            })
    }

    private fun fillData(comments: List<Comment>) {
        if (page == 1) {
            mComments = ArrayList(comments)
            adapter.replaceAllItems(mComments.toMutableList())
        } else {
            mComments.addAll(comments)
            adapter.addMoreItems(mComments.toMutableList())
        }

        isLastPage = mComments.size < limit || comments.isEmpty()

        if (!isLastPage) {
            page++
        }

        srlComments.isRefreshing = false
        rvComments.setVisible(mComments.isNotEmpty())
        llNoData.setVisible(mComments.isEmpty())
    }
}