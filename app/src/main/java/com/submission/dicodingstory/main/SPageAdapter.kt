package com.submission.dicodingstory.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.submission.dicodingstory.databinding.ItemStoryBinding
import com.submission.dicodingstory.model.Story
import com.submission.dicodingstory.util.setImage

class SPageAdapter(private val context: Context) :
    PagingDataAdapter<Story, SPageAdapter.ViewHolder>(
        differCallback
    ) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Story>()
        {
            override fun areItemsTheSame(oldItem: Story, newItem: Story):
                    Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Story, newItem: Story):
                    Boolean =
                oldItem == newItem}
    }


    private var listener: ((Story) -> Unit)? = null
    fun setOnItemClick(listener: (Story) -> Unit)
    {
        this.listener= listener}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)}

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val nowStory = getItem(position)
        if (nowStory != null) holder.bind(nowStory, listener)}

    inner class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Story, listener: ((Story) -> Unit)?) {
            with(binding) {
                ivItemPhoto.setImage(data.photoUrl)
                tvItemName.text = data.name
                viewRoot.setOnClickListener {
                    listener?.let { listener(data)}}}}
    }

}