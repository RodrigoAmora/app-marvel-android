package br.com.rodrigoamora.marvellapp.ui.recyclerview.callback

import androidx.recyclerview.widget.DiffUtil
import br.com.rodrigoamora.marvellapp.model.Character

class CharacterDiffCallback(
    private val mOldList: List<Character> = listOf(),
    private val mNewList: List<Character> = listOf()
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldList.size

    override fun getNewListSize(): Int = mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = mOldList[oldItemPosition].id == mNewList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCharacter = mOldList[oldItemPosition]
        val newCharacter = mNewList[newItemPosition]
        return oldCharacter.id == newCharacter.id
    }
}