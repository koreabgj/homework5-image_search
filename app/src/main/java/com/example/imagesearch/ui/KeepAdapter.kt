package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemLayoutBinding

class KeepAdapter(
    private val thumbnailUrlList: MutableList<String>,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<KeepAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(thumbnailUrlList: String)
    }

    inner class ImageViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(thumbnailUrlList[position])
                }
            }
        }

        fun bind(thumbnailUrlList: String) {
            binding.apply {
                Glide.with(root)
                    .load(thumbnailUrlList)
                    .into(ivThumbnail)

                tvSite.text = thumbnailUrlList
                tvDatetime.text = thumbnailUrlList
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(thumbnailUrlList[position])
    }

    override fun getItemCount(): Int {
        return thumbnailUrlList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<String>) {
        thumbnailUrlList.clear()
        thumbnailUrlList.addAll(newList)
        notifyDataSetChanged()
    }
}