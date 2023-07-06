package br.com.rodrigoamora.marvellapp.ui.recyclerview.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.extension.loadCircleImageWithGlide
import br.com.rodrigoamora.marvellapp.model.Character

class ListCharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var ivCharacter: ImageView
    lateinit var tvNameCharacter: TextView

    fun setValues(context: Context, character: Character) {
        val imageCharacterURL = "${character.thumbnail.path}.${character.thumbnail.extension}"

        ivCharacter = itemView.findViewById(R.id.iv_image_character)
        ivCharacter.loadCircleImageWithGlide(context, imageCharacterURL)

        tvNameCharacter = itemView.findViewById(R.id.tv_name_character_value)
        tvNameCharacter.text = character.name
    }

}