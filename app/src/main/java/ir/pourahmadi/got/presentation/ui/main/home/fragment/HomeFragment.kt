package ir.pourahmadi.got.presentation.ui.main.home.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khorasannews.latestnews.presentation.ui.main.home.adapter.onClicks.HousesClickListener
import dagger.hilt.android.AndroidEntryPoint
import ir.pourahmadi.got.R
import ir.pourahmadi.got.data.remote.dto.HousesRequest
import ir.pourahmadi.got.domain.common.MAX_PAGE_NUMBER
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.presentation.common.snack
import ir.pourahmadi.got.presentation.common.visibility
import ir.pourahmadi.got.presentation.ui.base.MainBaseFragment
import ir.pourahmadi.got.presentation.ui.main.home.adapter.HousesAdapter
import ir.pourahmadi.got.presentation.ui.main.home.viewModels.HomeViewModel
import ir.pourahmadi.got.presentation.ui.main.home.viewModels.State
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.mainLayout
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : MainBaseFragment(R.layout.fragment_home),
    HousesClickListener {

    val viewModel: HomeViewModel by viewModels()
    private val adapters by lazy { HousesAdapter() }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    var page: Int = 1

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 1
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                page++
                viewModel.getHouses(HousesRequest(page))
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initList()
        observe()
    }

    private fun init() {
        viewModel.getHouses(HousesRequest(page))

        trHome.apply {

        }

    }

    private fun initList() {
        rvHouses.apply {
            adapter = adapters
            addOnScrollListener(this@HomeFragment.scrollListener)
        }
        adapters.setListener(this)
    }


    private fun observe() {
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: State) {
        when (state) {
            is State.Init -> Unit
            is State.Success -> handleSuccess(state.mModel)
            is State.ShowToast -> mainLayout.snack(state.message) {}
            is State.IsLoading -> handleLoading(state.isLoading)
            is State.ShowResIdToast -> {
                mainLayout.snack(state.resId) {}
            }
        }
    }

    private fun handleSuccess(mModel: List<HousesModel>?) {
        mModel?.let {
            isLastPage = page == MAX_PAGE_NUMBER
            adapters.differ.submitList(it.toList())
        }
    }


    private fun handleLoading(isLoading: Boolean) {
        pbHouses.visibility(isLoading)
    }

    override fun onItemClick(clickModel: HousesModel) {
        val bundle = bundleOf("url" to clickModel.url , "characterUrl" to clickModel.founder)
        navController.navigate(R.id.action_HomeFragment_to_DetailFragment, bundle)
    }

}
