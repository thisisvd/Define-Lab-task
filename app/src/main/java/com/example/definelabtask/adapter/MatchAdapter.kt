package com.example.definelabtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.definelabtask.R
import com.example.definelabtask.constants.Constants.matchesMetaData
import com.example.definelabtask.data.model.Venue
import com.example.definelabtask.data.room.MatchData
import com.example.definelabtask.databinding.MatchedItemLayoutBinding

class MatchAdapter(var context: Context) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    // diff util call back
    private val differCallback = object : DiffUtil.ItemCallback<Venue>() {
        override fun areItemsTheSame(
            oldItem: Venue, newItem: Venue
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Venue, newItem: Venue
        ): Boolean {
            return oldItem == newItem
        }
    }

    // differ value setup
    val differ = AsyncListDiffer(this, differCallback)

    // setup recycler components
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding =
            MatchedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {

        val venue = differ.currentList[position]

        // image View
        holder.binding.apply {

            // name
            userName.text = venue.name

            // id
            userId.text = venue.id

            // city
            userCity.text =
                "${venue.location.formattedAddress[0]}, ${venue.location.city} ${venue.location.state}, ${venue.location.country}"

            // image
            if (matchesMetaData.contains(MatchData(venue.id, venue.name))) {
                starImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.baseline_star_24
                    )
                )
            } else {
                starImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.baseline_star_outline_24
                    )
                )
            }

            // on click
            root.setOnClickListener {
                onItemClickListener?.let { it(venue, starImg) }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    // inner Class ViewHolder
    inner class MatchViewHolder(val binding: MatchedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    // on click listener
    private var onItemClickListener: ((Venue, ImageView) -> Unit)? = null

    fun setOnItemClickListener(listener: (Venue, ImageView) -> Unit) {
        onItemClickListener = listener
    }
}