package br.com.rodrigoamora.marvellapp.ui.recyclerview.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Character

class ListCharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var tvNameCharacter: TextView

    fun setValues(character: Character) {
        tvNameCharacter = itemView.findViewById(R.id.tv_name_character_value)
        tvNameCharacter.text = character.name
    }

}