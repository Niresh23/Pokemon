package com.nik.pokemon.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("poster")
fun loadPoster(view: ImageView, image: Bitmap) {
    view.setImageBitmap(image)
}

