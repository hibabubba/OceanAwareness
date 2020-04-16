package com.example.prosjektin2000

import android.view.View

interface ItemClickListener {
    fun onClick(
        view: View?,
        position: Int,
        isLongClick: Boolean
    )
}