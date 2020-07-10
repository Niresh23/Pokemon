package com.nik.pokemon.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.Single
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

fun encode(image: Bitmap): String {
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.PNG, 100, baos)
    var imageBytes = baos.toByteArray()
    return Base64.getEncoder().encodeToString(imageBytes)
}

fun decode(imageView: ImageView, imageString: String) {
    val imageBytes = Base64.getDecoder().decode(imageString)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    imageView.setImageBitmap(decodedImage)
}

fun getImageFromUrl(url: String): Single<Bitmap> =
    Single.create{emitter ->
        Picasso.get().load(url).into(object : Target{
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                e?.let { emitter.onError(e) }
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let { emitter.onSuccess(it) }
            }
        })
    }



