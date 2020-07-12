package com.nik.pokemon.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
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