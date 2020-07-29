package com.example.paginglibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paginglibrary.R
import com.example.paginglibrary.databinding.ReposLoadStateFooterViewItemBinding
import com.example.paginglibrary.extension.isVisible


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 26-07-2020 12:01
*/

class ReposLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ReposLoadStateAdapter.ReposLoadStateViewHolder>() {
    class ReposLoadStateViewHolder(val binding: ReposLoadStateFooterViewItemBinding,
                                         retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible(loadState is LoadState.Loading)
            binding.retryButton.isVisible(loadState !is LoadState.Loading)
            binding.errorMsg.isVisible(loadState !is LoadState.Loading)
        }
        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repos_load_state_footer_view_item, parent, false)
                val binding = ReposLoadStateFooterViewItemBinding.bind(view)
                return ReposLoadStateViewHolder(binding, retry)
            }
        }
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder.create(parent, retry)
    }
}