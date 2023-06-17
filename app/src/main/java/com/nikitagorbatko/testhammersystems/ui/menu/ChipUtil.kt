package com.nikitagorbatko.testhammersystems.ui.menu

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nikitagorbatko.testhammersystems.R

object ChipUtil {
    fun getChip(
        context: Context,
        chipGroup: ChipGroup,
        label: String,
        @ColorRes chipColorChecked: Int,
        @ColorRes chipColor: Int,
        @ColorInt chipTextChecked: Int,
        @ColorInt chipText: Int
    ): Chip {
        return Chip(context).apply {
            id = View.generateViewId()
            text = label
            isClickable = true
            isCheckable = true
            height = 35
            chipGroup.setChipSpacingHorizontalResource(R.dimen.dimen_8dp)
            chipBackgroundColor = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()), intArrayOf(
                    R.color.pink_200,
                    chipColor
                )
            )
            setOnCheckedChangeListener { buttonView, isChecked ->
                setTextColor(if (isChecked) chipTextChecked else chipText )
            }
            isCheckedIconVisible = false
            isFocusable = true
            chipGroup.addView(this)
        }
    }
}