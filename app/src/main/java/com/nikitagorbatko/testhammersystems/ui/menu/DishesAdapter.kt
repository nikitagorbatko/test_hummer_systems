package com.nikitagorbatko.testhammersystems.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikitagorbatko.testhammersystems.database.DishDbo
import com.nikitagorbatko.testhammersystems.databinding.ItemDishBinding

class DishesAdapter : PagingDataAdapter<DishDbo, DishesAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(val binding: ItemDishBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dish = getItem(position)
        viewHolder.binding.textName.text = dish?.name
        viewHolder.binding.textDescription.text = dish?.description
        viewHolder.binding.buttonPrice.text = "от 345 р"
        Glide
            .with(viewHolder.binding.root)
            .load(dish?.thumbnailUrl)
            .centerCrop()
            .into(viewHolder.binding.imageDish)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<DishDbo>() {
    override fun areItemsTheSame(oldItem: DishDbo, newItem: DishDbo): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(
        oldItem: DishDbo,
        newItem: DishDbo
    ): Boolean {
        return oldItem.seoTitle == newItem.seoTitle
    }
}