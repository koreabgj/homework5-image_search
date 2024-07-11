package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.R
import com.example.imagesearch.data.ImageDocuments
import com.example.imagesearch.databinding.ItemLayoutBinding

class SearchAdapter(
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var imageDocuments: List<ImageDocuments> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ImageDocuments>) {
        imageDocuments = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(thumbnail: ThumbnailModel)
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isLiked = false

        fun bind(item: ImageDocuments) {
            binding.apply {
                Glide.with(root)
                    .load(item.thumbnailUrl)
                    .into(ivThumbnail)

                tvSite.text = item.displaySiteName
                tvDatetime.text = item.dateTime.toString()

                binding.ivLike.setOnClickListener {
                    isLiked = !isLiked

                    val imageResource =
                        if (isLiked) R.drawable.img_favorite else R.drawable.img_empty_favorite
                    binding.ivLike.setImageResource(imageResource)

                    // 클릭한 이미지 전달
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val thumbnail = ThumbnailModel(
                            url = item.thumbnailUrl,
                            siteName = item.displaySiteName,
                            dateTime = item.dateTime.toString()
                        )
                        itemClickListener.onItemClick(thumbnail)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageDocuments[position])
    }

    override fun getItemCount(): Int {
        return imageDocuments.size
    }
}