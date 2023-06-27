package br.com.rodrigoamora.marvellapp.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.ui.recyclerview.viewholder.ListCharactersViewHolder

class ListCharactersAdapter(
    private val context: Context,
    private val charactersList: MutableList<Character> = mutableListOf()
) : RecyclerView.Adapter<ListCharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCharactersViewHolder {
        val view = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_list_characters, parent, false)
        return ListCharactersViewHolder(view)
    }

    override fun getItemCount(): Int = charactersList.size

    override fun onBindViewHolder(holder: ListCharactersViewHolder, position: Int) {
        holder.setValues(charactersList[position])
    }

    fun update(salonsList: List<Character>) {
        this.charactersList.clear()
        this.charactersList.addAll(salonsList)
        notifyDataSetChanged()
    }

}