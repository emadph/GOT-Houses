package ir.pourahmadi.got.presentation.ui.main.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.pourahmadi.got.R
import ir.pourahmadi.got.domain.model.HousesFounderCharacterModel
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.presentation.common.snack
import ir.pourahmadi.got.presentation.common.visibility
import ir.pourahmadi.got.presentation.ui.base.MainBaseFragment
import ir.pourahmadi.got.presentation.ui.main.detail.viewModels.DetailState
import ir.pourahmadi.got.presentation.ui.main.detail.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.mainLayout
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailFragment : MainBaseFragment(R.layout.fragment_detail) {

    var detailUrl: String = ""
    var characterUrl: String = ""
    val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.getDetailOfHouse(detailUrl)
        viewModel.getDetailOfHouseFounder(characterUrl)
        observe()
    }

    private fun init() {
        detailUrl = arguments!!.getString("url").toString()
        characterUrl = arguments!!.getString("characterUrl").toString()

        trDetail.apply {
            imgToolbarBack.setOnClickListener {
                navController.navigateUp()
            }
        }

    }

    private fun observe() {
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: DetailState) {
        when (state) {
            is DetailState.Init -> Unit
            is DetailState.Success -> handleSuccess(state.mModel)
            is DetailState.SuccessFounder -> handleSuccessFounder(state.mModel)
            is DetailState.ShowToast -> mainLayout.snack(state.message) {}
            is DetailState.IsLoading -> handleLoading(state.isLoading)
            is DetailState.ShowResIdToast -> {
                mainLayout.snack(state.resId) {}
            }
        }
    }

    private fun handleSuccessFounder(mModel: HousesFounderCharacterModel?) {
        mModel?.let {
            tvDetailFounder.text = it.name.toString()
            tvDetailFounderTitles.text = it.titles.toString()
        }

    }
    private fun handleSuccess(mModel: HousesModel?) {
        mModel?.let {
            tvDetailTitle.text = it.name.toString()
            tvDetailRegion.text = it.region.toString()
            tvDetailWords.text = it.words.toString()
            tvDetailTitles.text = it.titles.toString()
            tvDetailFounded.text = it.founded.toString()
            tvDetailFlagDesc.text = it.flagDesc.toString()
        }
    }


    private fun handleLoading(isLoading: Boolean) {
        pbDetail.visibility(isLoading)
    }

}
