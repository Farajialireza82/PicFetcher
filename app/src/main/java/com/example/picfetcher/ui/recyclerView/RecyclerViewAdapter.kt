package com.example.picfetcher.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picfetcher.databinding.ImageItemBinding
import com.example.picfetcher.model.ApiPhoto

class RecyclerViewAdapter(
    private var picsArray: ArrayList<ApiPhoto>
) : RecyclerView.Adapter<RecyclerViewAdapter.ImageView>() {

    private var onClickListener: OnClickListener? = null

    inner class ImageView(val itemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    fun updateListItems(newList: List<ApiPhoto>) {
        val newItems = picsArray + newList
        val picsDiffCallback = PicsCallback(picsArray, newItems)
        val diffResult = DiffUtil.calculateDiff(picsDiffCallback)

        picsArray.clear()
        picsArray.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageView = ImageView(
        ImageItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ImageView, position: Int) {
        Glide.with(holder.itemBinding.root)
            .load(picsArray[position].url)
            .into(holder.itemBinding.imageView)
        holder.itemBinding.idTextView.text = picsArray[position].id.toString()

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(picsArray[position])
            }
        }
    }


    override fun getItemCount(): Int = picsArray.size

    interface OnClickListener {
        fun onClick(apiPhoto: ApiPhoto)
    }
}
