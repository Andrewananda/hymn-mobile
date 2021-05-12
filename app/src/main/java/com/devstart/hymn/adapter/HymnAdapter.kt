package com.devstart.hymn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devstart.hymn.databinding.HymnItemBinding
import com.devstart.hymn.model.Song

class HymnAdapter(private val clickListener: OnClickListener) : ListAdapter<Song, HymnAdapter.NewsViewHolder>(HymnDiffUtil) {

    inner class NewsViewHolder(private val binding: HymnItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Song) {
            binding.title.text = item.title
            binding.hymnNumber.text = item.number.toString()
            binding.chorus.text = item.chorus
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(HymnItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
    }


    companion object HymnDiffUtil: DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class OnClickListener(val clickListener: (hymn : Song) -> Unit) {
        fun onClick(hymn: Song) = clickListener(hymn)
    }
}