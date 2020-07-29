package com.example.paginglibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paginglibrary.databinding.ItemCellBinding
import com.example.paginglibrary.model.UserModel

/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class Adapter : PagingDataAdapter<UserModel, Adapter.MyViewHolder>(
    DIFF_UTIL
) {

    companion object{
        val DIFF_UTIL = object: DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    }

    inner class MyViewHolder(val binding: ItemCellBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setupData(model: UserModel?){
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setupData(getItem(position))
    }
}