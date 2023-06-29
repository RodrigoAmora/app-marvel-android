package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListCharactersFragment: Fragment() {

    private lateinit var fabSearchCharacterByName: FloatingActionButton
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var searchView: SearchView
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

        fabSearchCharacterByName = root.findViewById(R.id.fab_search_character_by_name)
        fabSearchCharacterByName.setOnClickListener {
            if (searchView.visibility == View.GONE) {
                searchView.visibility = View.VISIBLE
            } else {
                searchView.visibility = View.GONE
            }
        }

        recyclerViewCharacters = root.findViewById(R.id.list_characters)

        searchView = root.findViewById(R.id.sv_search_character_by_name)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isEmpty()) {
                        characterActivity.getCharacters()
                    } else {
                        characterActivity.getCharacterByName(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        swipeRefresh = root.findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            getCharacters()
            swipeRefresh.isRefreshing = false
        }

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