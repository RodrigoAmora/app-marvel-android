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
    private lateinit var characters: List<Character>
    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    private var offset = 0

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        this._binding = FragmentListCharactersBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.fabSearchCharacterByName = binding.fabSearchCharacterByName
        this.fabSearchCharacterByName.setOnClickListener {
            if (this.searchView.visibility == View.GONE) {
                this.searchView.visibility = View.VISIBLE
                this.searchView.requestFocus()
            } else {
                this.searchView.visibility = View.GONE
            }
        }

        this.recyclerViewCharacters = this.binding.listCharacters

        this.searchView = this.binding.svSearchCharacterByName
        this.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        this.swipeRefresh = this.binding.swipeRefresh
        this.swipeRefresh.setOnRefreshListener {
            this.playSound()
            this.getCharacters()
            this.swipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.configureRecyclerView()
        this.configureAdapter()
        this.getCharacters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    private fun configureRecyclerView() {
        val linearLayout = LinearLayoutManager(this.mainActivity,
                                               LinearLayoutManager.VERTICAL,
                                    false)
        linearLayout.scrollToPosition(0)

        val dividerItemDecoration = DividerItemDecoration(this.mainActivity,
                                                          DividerItemDecoration.VERTICAL)

        this.adapter = ListCharactersAdapter(this.mainActivity.applicationContext)

        this.recyclerViewCharacters.adapter = this.adapter
        this.recyclerViewCharacters.addItemDecoration(dividerItemDecoration)
        this.recyclerViewCharacters.setHasFixedSize(true)
        this.recyclerViewCharacters.itemAnimator = DefaultItemAnimator()
        this.recyclerViewCharacters.layoutManager = linearLayout
        this.recyclerViewCharacters.isNestedScrollingEnabled = true
        this.recyclerViewCharacters.scrollToPosition(adapter.itemCount - 1)
        this.recyclerViewCharacters.addOnScrollListener(object : RecyclerViewPaginateListener(linearLayout) {
            override fun onLoadMore(currentPage: Int) {
                if (characters.size >= 20) {
                    offset += 20
                    getCharacters()
                }
            }
        })

        this.recyclerViewCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && fabSearchCharacterByName.isShown()) fabSearchCharacterByName.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) fabSearchCharacterByName.show()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun configureAdapter() {
        this.adapter.whenSelected = this::viewDetails
    }

    private fun playSound() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this.mainActivity, R.raw.swipe_sound)
        mediaPlayer.start()
    }

    private fun populateRecyclerView(characters: List<Character>) {
        this.characters = characters
        this.adapter.update(characters)
    }

    private fun replaceRecyclerView(characters: List<Character>) {
        this.characters = characters
        this.searchView.visibility = View.GONE
        this.adapter.replaceAll(characters)
    }

    private fun getCharacters() {
        this.characterViewModel.getCharacters(this.offset).observe(this.mainActivity,
            Observer { characters ->
                characters.result?.let {
                    populateRecyclerView(it)
                }
                characters.error?.let { showError(mainActivity, it) }

                if (!NetworkUtil.checkConnection(mainActivity)) {
                    this.showToast(mainActivity, getString(R.string.error_no_internet))
                }
            }
        )
    }

    private fun getCharacterByName(name: String) {
        if (NetworkUtil.checkConnection(this.mainActivity)) {
            this.characterViewModel.getCharacterByName(name).observe(this,
                Observer { characters ->
                    characters.result?.let {
                        replaceRecyclerView(it)
                    }
                    characters.error?.let {
                        showError(mainActivity, it)
                    }
                }
            )
        } else {
            this.showToast(this.mainActivity, getString(R.string.error_no_internet))
        }
    }

    @SuppressLint("ResourceType")
    private fun viewDetails(character: Character) {
        val characterBundle = Bundle()
        characterBundle.putSerializable("character", character)
        Navigation.findNavController(this.recyclerViewCharacters)
                  .navigate(R.id.action_nav_list_characters_to_nav_character, characterBundle)
    }

}
