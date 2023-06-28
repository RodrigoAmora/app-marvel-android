package br.com.rodrigoamora.marvellapp.ui.recyclerview.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Thumbnail
import com.bumptech.glide.Glide

class ListCharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var ivCharacter: ImageView
    lateinit var tvNameCharacter: TextView

    fun setValues(context: Context, character: Character) {
        ivCharacter = itemView.findViewById(R.id.iv_image_character)
        loadImage(context, character.thumbnail)

        tvNameCharacter = itemView.findViewById(R.id.tv_name_character_value)
        tvNameCharacter.text = character.name
    }

    private fun loadImage(context: Context, thumbnail: Thumbnail) {
        val imageCharacterURL = "${thumbnail.path}.${thumbnail.extension}"

        Glide.with(context)
            .load(imageCharacterURL)
            .placeholder(R.drawable.ic_menu_characters)
            .circleCrop()
            .into(ivCharacter)
    }

}