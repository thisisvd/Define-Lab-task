package com.example.definelabtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.definelabtask.data.room.MatchData
import com.example.definelabtask.databinding.SavedMatchedItemLayoutBinding

class SavedMatchAdapter : RecyclerView.Adapter<SavedMatchAdapter.MatchViewHolder>() {

    // diff util call back
    private val differCallback = object : DiffUtil.ItemCallback<MatchData>() {
        override fun areItemsTheSame(
            oldItem: MatchData, newItem: MatchData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MatchData, newItem: MatchData
        ): Boolean {
            return oldItem == newItem
        }
    }

    // differ value setup
    val differ = AsyncListDiffer(this, differCallback)

    // setup recycler components
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = SavedMatchedItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {

        val savedMatchData = differ.currentList[position]

        // image View
        holder.binding.apply {

            // name
            userName.text = "ID : ${savedMatchData.name}"

            // id
            userId.text = "Name : ${savedMatchData.id}"

            // on click
            deleteImg.setOnClickListener {
                onItemClickListener?.let { it(savedMatchData) }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    // inner Class ViewHolder
    inner class MatchViewHolder(val binding: SavedMatchedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    // on click listener
    private var onItemClickListener: ((MatchData) -> Unit)? = null

    fun setOnItemClickListener(listener: (MatchData) -> Unit) {
        onItemClickListener = listener
    }
}