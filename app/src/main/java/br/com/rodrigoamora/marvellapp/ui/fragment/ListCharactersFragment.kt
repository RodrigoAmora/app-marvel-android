package br.com.rodrigoamora.marvellapp.ui.fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.databinding.FragmentListCharactersBinding
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.ui.activity.MainActivity
import br.com.rodrigoamora.marvellapp.ui.recyclerview.adapter.ListCharactersAdapter
import br.com.rodrigoamora.marvellapp.ui.recyclerview.listener.RecyclerViewPaginateListener
import br.com.rodrigoamora.marvellapp.ui.viewmodel.CharacterViewModel
import br.com.rodrigoamora.marvellapp.util.NetworkUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListCharactersFragment: BaseFragment() {

    private var _binding: FragmentListCharactersBinding? = null
    private val binding get() = _binding!!

    private val characterViewModel: CharacterViewModel by viewModel()

    private lateinit var fabSearchCharacterByName: FloatingActionButton
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    private lateinit var adapter: ListCharactersAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var characters: List<Character>
    private var offset = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fabSearchCharacterByName = binding.fabSearchCharacterByName
        fabSearchCharacterByName.setOnClickListener {
            if (searchView.visibility == View.GONE) {
                searchView.visibility = View.VISIBLE
            } else {
                searchView.visibility = View.GONE
            }
        }

        recyclerViewCharacters = binding.listCharacters

        searchView = binding.svSearchCharacterByName
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    getCharacterByName(it.trim())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener {
            playSound()
            getCharacters()
            swipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recoveryActivity()
        configureRecyclerView()
        configureAdapter()
        getCharacters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureRecyclerView() {
        val linearLayout = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,
            false)
        linearLayout.scrollToPosition(0)

        val dividerItemDecoration = DividerItemDecoration(activity,
            DividerItemDecoration.VERTICAL)

        adapter = ListCharactersAdapter(mainActivity.applicationContext)

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
        val mediaPlayer: MediaPlayer = MediaPlayer.create(mainActivity, R.raw.swipe_sound)
        mediaPlayer.start()
    }

    fun populateRecyclerView(characters: List<Character>) {
        this.characters = characters
        adapter.update(characters)
    }

    fun replaceRecyclerView(characters: List<Character>) {
        this.characters = characters
        searchView.visibility = View.GONE
        adapter.replaceAll(characters)
    }

    private fun getCharacters() {
        characterViewModel.getCharacters(offset).observe(mainActivity,
            Observer { characters ->
                characters.result?.let {
                    populateRecyclerView(it)
                }
                characters.error?.let { showError(mainActivity, it) }

                if (!NetworkUtil.checkConnection(mainActivity)) {
                    showToast(mainActivity, getString(R.string.error_no_internet))
                }
            }
        )
    }

    private fun getCharacterByName(name: String) {
        if (NetworkUtil.checkConnection(mainActivity)) {
            characterViewModel.getCharacterByName(name).observe(this,
                Observer { characters ->
                    characters.result?.let {
                        replaceRecyclerView(it)
                    }
                    characters.error?.let { showError(mainActivity, it) }
                }
            )
        } else {
            showToast(mainActivity, getString(R.string.error_no_internet))
        }
    }

    @SuppressLint("ResourceType")
    private fun viewDetails(character: Character) {
        val characterBundle = Bundle()
        characterBundle.putSerializable("character", character)
        Navigation.findNavController(recyclerViewCharacters)
                .navigate(R.id.action_nav_list_characters_to_nav_character, characterBundle)

    }

    @SuppressLint("RestrictedApi")
    private fun recoveryActivity() {
        mainActivity = activity as MainActivity
    }
}