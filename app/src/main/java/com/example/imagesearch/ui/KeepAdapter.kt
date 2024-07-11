package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemLayoutBinding

class KeepAdapter(
    private val thumbnailList: MutableList<ThumbnailModel>,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<KeepAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(thumbnail: ThumbnailModel)
    }

    inner class ImageViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(thumbnailList[position])
                }
            }
        }

        fun bind(thumbnail: ThumbnailModel) {
            binding.apply {
                Glide.with(root)
                    .load(thumbnail.url)
                    .into(ivThumbnail)

                tvSite.text = thumbnail.siteName
                tvDatetime.text = thumbnail.dateTime
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
        holder.bind(thumbnailList[position])
    }

    override fun getItemCount(): Int {
        return thumbnailList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ThumbnailModel>) {
        thumbnailList.clear()
        thumbnailList.addAll(newList)
        notifyDataSetChanged()
    }
}