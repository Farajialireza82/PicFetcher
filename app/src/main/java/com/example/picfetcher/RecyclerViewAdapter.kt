package com.example.picfetcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picfetcher.databinding.ImageItemBinding

class RecyclerViewAdapter(
    private var picsArray: ArrayList<ApiPhoto>
) : RecyclerView.Adapter<RecyclerViewAdapter.ImageView>() {

    fun updateList(newList: List<ApiPhoto>) {
        newList.forEach {
            picsArray.add(it)
        }
        notifyDataSetChanged()
    }

    inner class ImageView(val itemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ImageView = ImageView(
        ImageItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ImageView, position: Int) {
        Glide.with(holder.itemBinding.root)
            .load(picsArray[position].url)
            .into(holder.itemBinding.imageView)
        holder.itemBinding.idTextView.text = picsArray[position].id.toString()
    }


    override fun getItemCount(): Int = picsArray.size

}