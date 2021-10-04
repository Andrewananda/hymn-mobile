package com.devstart.hymn.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devstart.hymn.databinding.HymnItemBinding
import com.devstart.hymn.data.model.Song
import com.devstart.hymn.data.model.SongResponse

class HymnAdapter(private val clickListener: OnClickListener) : ListAdapter<SongResponse, HymnAdapter.NewsViewHolder>(
    HymnDiffUtil
) {

    inner class NewsViewHolder(private val binding: HymnItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongResponse) {
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


    companion object HymnDiffUtil: DiffUtil.ItemCallback<SongResponse>() {
        override fun areItemsTheSame(oldItem: SongResponse, newItem: SongResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SongResponse, newItem: SongResponse): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class OnClickListener(val clickListener: (hymn : SongResponse) -> Unit) {
        fun onClick(hymn: SongResponse) = clickListener(hymn)
    }
}