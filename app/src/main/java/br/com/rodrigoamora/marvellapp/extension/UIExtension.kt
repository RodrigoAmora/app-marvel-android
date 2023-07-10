package br.com.rodrigoamora.marvellapp.extension

import android.content.Context
import android.widget.ImageView
import br.com.rodrigoamora.marvellapp.R
import com.bumptech.glide.Glide

fun ImageView.loadCircleImageWithGlide(context: Context, imageURL: String) {
    Glide.with(context)
        .load(imageURL)
        .placeholder(R.drawable.ic_menu_characters)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageWithGlide(context: Context, imageURL: String) {
    Glide.with(context)
        .load(imageURL)
        .placeholder(R.drawable.ic_menu_characters)
        .into(this)
}