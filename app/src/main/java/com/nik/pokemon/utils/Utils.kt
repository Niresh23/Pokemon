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

object Utils {
    fun encode(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.getEncoder().encodeToString(imageBytes)
    }

    fun decode( imageString: String?): Bitmap {
        val imageBytes = Base64.getDecoder().decode(imageString)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun getImageFromUrl(url: String?): Bitmap =
        Picasso.get().load(url).get()
}