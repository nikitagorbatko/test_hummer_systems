package com.nikitagorbatko.testhammersystems.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikitagorbatko.testhammersystems.databinding.ItemCategoryBinding
import com.nikitagorbatko.testhammersystems.entity.Category


class CategoriesAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = categories[position]
        viewHolder.binding.textCategory.text = category.name
//        Glide
//            .with(viewHolder.binding.root)
//            .load(category.imageUrl)
//            .into(viewHolder.binding.imageCategory)
    }

    override fun getItemCount() = categories.size
}
