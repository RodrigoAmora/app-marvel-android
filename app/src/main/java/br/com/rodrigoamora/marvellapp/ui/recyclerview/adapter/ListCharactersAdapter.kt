package br.com.rodrigoamora.marvellapp.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.ui.recyclerview.callback.CharacterDiffCallback
import br.com.rodrigoamora.marvellapp.ui.recyclerview.viewholder.ListCharactersViewHolder


class ListCharactersAdapter(
    private val context: Context,
    private val characters: MutableList<Character> = mutableListOf(),
    var whenSelected: (character: Character) -> Unit = {}
) : RecyclerView.Adapter<ListCharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCharactersViewHolder {
        val view = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_list_characters, parent, false)
        return ListCharactersViewHolder(view)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: ListCharactersViewHolder, position: Int) {
        val character = characters[position]

        holder.setValues(context, character)
        holder.itemView.setOnClickListener {
            whenSelected(character)
        }
    }

    fun update(characters: List<Character>) {
        val diffCallback = CharacterDiffCallback(this.characters, characters)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.characters.clear()
        this.characters.addAll(characters)

        diffResult.dispatchUpdatesTo(this)
    }

    fun replaceAll(characters: List<Character>) {
        this.characters.clear()
        this.characters.addAll(characters)
        notifyDataSetChanged()
    }
}