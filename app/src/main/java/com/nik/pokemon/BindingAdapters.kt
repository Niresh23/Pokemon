package com.nik.pokemon

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("poster")
fun loadPoster(view: ImageView, url: String?) {
    url?.let {
        if (url.isNotEmpty()) {
            Picasso.get().load(url).into(view)
        }
        else {
            view.setImageResource(R.drawable.ic_no_poster)
        }
    }
}

fun ldPostr(view: ImageView, image: Bitmap) {
    view.setImageBitmap(image)
}

fun hfh(url: String): Bitmap {
    return Picasso.get().load(url).get()
}