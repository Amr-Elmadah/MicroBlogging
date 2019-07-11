package com.tasks.microblogging.ui.authorslist.presetation.view.adapter

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.microblogging.R
import com.tasks.microblogging.base.presentation.view.adapter.BaseRecyclerAdapter
import com.tasks.microblogging.base.presentation.view.extension.getInflatedView
import com.tasks.microblogging.data.remote.network.response.Author
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_author.view.*


class AuthorsAdapter : BaseRecyclerAdapter<Author>() {

    private val mViewClickSubject = PublishSubject.create<Author>()

    fun getViewClickedObservable(): Observable<Author> {
        return mViewClickSubject
    }

    override fun getAdapterPageSize(): Int {
        return PAGE_SIZE
    }

    override fun mainItemView(parent: ViewGroup): View {
        return parent.getInflatedView(R.layout.item_author)
    }


    override fun mainItemViewHolder(view: View): RecyclerView.ViewHolder {
        return AuthorViewHolder(view)
    }

    override fun onBindMainViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AuthorViewHolder) {
            holder.bind(getItems()[position])
            holder.itemView.setOnClickListener {
                mViewClickSubject.onNext(getItems()[position])
            }
        }
    }

    private class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Author) = with(itemView) {
            tvAuthorName.text = item.name
            tvAuthorEmail.text = item.email
            Glide.with(context).load(item.avatarUrl).into(imgAuthor)
        }
    }
}