package com.submission.dicodingstory.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.submission.dicodingstory.databinding.LoadStateFooterBinding

class StoryLoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<StoryLoadingAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder
    {
        val binding =
            LoadStateFooterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, retry)}

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState)
    {
        holder.bind(loadState)}


    class ViewHolder(private val binding: LoadStateFooterBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root)
    {

        init
        {
            binding.btnRetry.setOnClickListener { retry.invoke()}}



        fun bind(loadState: LoadState)
        {
            if (loadState is LoadState.Error) binding
                .tvError.text =
                loadState.error.localizedMessage
            binding.progress.isVisible = loadState is LoadState.Loading
            binding.tvError.isVisible = loadState is LoadState.Error
            binding.btnRetry.isVisible = loadState is LoadState.Error}}

}