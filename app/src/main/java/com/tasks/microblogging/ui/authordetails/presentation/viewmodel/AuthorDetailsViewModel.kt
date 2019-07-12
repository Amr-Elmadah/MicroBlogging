package com.tasks.microblogging.ui.authordetails.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tasks.microblogging.base.domain.exception.MicroBloggingException
import com.tasks.microblogging.base.presentation.model.ObservableResource
import com.tasks.microblogging.base.presentation.viewmodel.BaseViewModel
import com.tasks.microblogging.data.remote.network.response.Post
import com.tasks.microblogging.data.remote.network.response.PostsParams
import com.tasks.microblogging.ui.authordetails.domain.interactor.GetAuthorPostsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthorDetailsViewModel @Inject constructor(
    private val getAuthorPostsUseCase: GetAuthorPostsUseCase
) : BaseViewModel() {
    private var postsList = mutableListOf<Post>()
    val mPosts = MutableLiveData<List<Post>>()
    val mPostsObservable = ObservableResource<String>()

    fun getAuthorPosts(postsParams: PostsParams) {
        addDisposable(getAuthorPostsUseCase.build(params = postsParams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mPostsObservable.loading.postValue(true)
            }
            .doAfterTerminate {
                mPostsObservable.loading.postValue(false)
            }
            .subscribe({
                it?.let {
                    if (it.isNotEmpty()) {
                        postsList = it.toMutableList()
                        mPosts.value = postsList
                    }
                }
            }, {
                (it as? MicroBloggingException).let {
                    mPostsObservable.error.value = it
                }
            })
        )
    }
}
