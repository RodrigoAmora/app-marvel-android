package br.com.rodrigoamora.marvellapp.ui.recyclerview.paginate

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class CharacterPaginate(private var linearLayoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {

    var currentPage : Int = 1
    var loading = true
    var previousTotal : Int = 0
    var visibleThreshold : Int = 5

    var firstVisibleItem : Int? = null
    var visibleItemCount : Int? = null
    var totalItemCount : Int? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager?.itemCount
        firstVisibleItem = linearLayoutManager?.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount!! > previousTotal) {
                loading = false
                previousTotal = totalItemCount!!
            }
        }

        if (!loading && (totalItemCount!! - visibleItemCount!!) <= (firstVisibleItem!! + visibleThreshold)) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }

    abstract fun onLoadMore(currentPage: Int)

}