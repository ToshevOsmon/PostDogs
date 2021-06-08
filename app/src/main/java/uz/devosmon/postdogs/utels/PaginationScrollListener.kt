package uz.devosmon.postdogs.utels

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val childCount: Int = layoutManager.childCount
        val itemCount = layoutManager.itemCount
        val findFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()



        if (!isLoading() && !isLastPage()){
            if ((childCount+ findFirstVisibleItemPosition)>=itemCount
                && findFirstVisibleItemPosition>=0
                && itemCount >= getTotalPageCount()){
                loadMoreItems()
            }
        }

    }


    abstract fun loadMoreItems()

    abstract fun getTotalPageCount():Int

    abstract  fun isLastPage():Boolean

    abstract  fun isLoading():Boolean


}