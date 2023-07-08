package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import br.com.rodrigoamora.marvellapp.ui.recyclerview.listener.RecyclerViewPaginateListener
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListCharactersFragment: Fragment() {

    private lateinit var fabSearchCharacterByName: FloatingActionButton
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    private lateinit var adapter: ListCharactersAdapter
    private lateinit var characterActivity: CharacterActivity
    private lateinit var characters: List<Character>
    private var offset = 0

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
                        getCharacters()
                    } else {
                        getCharacterByName(it)
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
            playSound()
            getCharacters()
            swipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharactersActivity()
        configureRecyclerView()
        configureAdapter()
        getCharacters()
    }

    private fun configureRecyclerView() {
        val linearLayout = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,
            false)
        linearLayout.scrollToPosition(0)

        val dividerItemDecoration = DividerItemDecoration(activity,
            DividerItemDecoration.VERTICAL)

        adapter = ListCharactersAdapter(characterActivity.applicationContext)

        recyclerViewCharacters.adapter = adapter
        recyclerViewCharacters.addItemDecoration(dividerItemDecoration)
        recyclerViewCharacters.setHasFixedSize(true)
        recyclerViewCharacters.itemAnimator = DefaultItemAnimator()
        recyclerViewCharacters.layoutManager = linearLayout
        recyclerViewCharacters.isNestedScrollingEnabled = true
        recyclerViewCharacters.scrollToPosition(adapter.itemCount - 1)
        recyclerViewCharacters.addOnScrollListener(object : RecyclerViewPaginateListener(linearLayout) {
            override fun onLoadMore(currentPage: Int) {
                if (characters.size >= 20) {
                    offset += 20
                    getCharacters()
                }
            }
        })
    }

    private fun configureAdapter() {
        adapter.whenSelected = this::viewDetails
    }

    private fun playSound() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(characterActivity, R.raw.swipe_sound)
        mediaPlayer.start()
    }

    fun populateRecyclerView(characters: List<Character>) {
        this.characters = characters
        adapter.update(characters)
    }

    private fun getCharacters() {
        characterActivity.getCharacters(offset)
    }

    private fun getCharacterByName(name: String) {
        characterActivity.getCharacterByName(name)
    }

    private fun getCharactersActivity() {
        characterActivity = activity as CharacterActivity
    }

    private fun viewDetails(character: Character) {
        val bundle = Bundle()
        bundle.putSerializable("character", character)

        characterActivity.changeFragment(CharacterFragment(), bundle, true)
    }
}