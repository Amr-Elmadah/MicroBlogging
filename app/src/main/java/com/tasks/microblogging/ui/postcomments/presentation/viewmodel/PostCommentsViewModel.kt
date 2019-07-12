package com.tasks.microblogging.ui.postcomments.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tasks.microblogging.base.domain.exception.MicroBloggingException
import com.tasks.microblogging.base.presentation.model.ObservableResource
import com.tasks.microblogging.base.presentation.viewmodel.BaseViewModel
import com.tasks.microblogging.data.remote.network.response.Comment
import com.tasks.microblogging.data.remote.network.response.CommentsParams
import com.tasks.microblogging.ui.postcomments.domain.interactor.GetPostCommentsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostCommentsViewModel @Inject constructor(
    private val getPostCommentsUseCase: GetPostCommentsUseCase
) : BaseViewModel() {
    private var commentsList = mutableListOf<Comment>()
    val mComments = MutableLiveData<List<Comment>>()
    val mCommentsObservable = ObservableResource<String>()

    fun getPostComments(commentsParams: CommentsParams) {
        addDisposable(getPostCommentsUseCase.build(params = commentsParams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mCommentsObservable.loading.postValue(true)
            }
            .doAfterTerminate {
                mCommentsObservable.loading.postValue(false)
            }
            .subscribe({
                it?.let {
                    if (it.isNotEmpty()) {
                        commentsList = it.toMutableList()
                        mComments.value = commentsList
                    }
                }
            }, {
                (it as? MicroBloggingException).let {
                    mCommentsObservable.error.value = it
                }
            })
        )
    }
}