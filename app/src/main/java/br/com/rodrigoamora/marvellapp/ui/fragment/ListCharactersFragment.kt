package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.ui.activity.CharacterActivity
import br.com.rodrigoamora.marvellapp.ui.recyclerview.adapter.ListCharactersAdapter

class ListCharactersFragment: Fragment() {

    private lateinit var recyclerViewCharacters : RecyclerView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    private lateinit var adapter: ListCharactersAdapter
    private lateinit var characterActivity: CharacterActivity

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(
            R.layout.fragment_list_characters,
            container,
            false
        )

        recyclerViewCharacters = root.findViewById(R.id.list_characters)
        swipeRefresh = root.findViewById(R.id.swipe_refresh)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharactersActivity()
        configureRecyclerView()
        getCharacters()
    }

    private fun configureRecyclerView() {
        val linearLayout = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,
            false)
        val dividerItemDecoration = DividerItemDecoration(activity,
            DividerItemDecoration.VERTICAL)

        adapter = ListCharactersAdapter(characterActivity.applicationContext)

        recyclerViewCharacters.adapter = adapter
        recyclerViewCharacters.addItemDecoration(dividerItemDecoration)
        recyclerViewCharacters.setHasFixedSize(true)
        recyclerViewCharacters.itemAnimator = DefaultItemAnimator()
        recyclerViewCharacters.layoutManager = linearLayout
        recyclerViewCharacters.isNestedScrollingEnabled = false
    }

    fun populateRecyclerView(charactersList: List<Character>) {
        adapter.update(charactersList)
    }

    private fun getCharacters() {
        characterActivity.getCharacters()
    }

    private fun getCharactersActivity() {
        characterActivity = activity as CharacterActivity
    }

}