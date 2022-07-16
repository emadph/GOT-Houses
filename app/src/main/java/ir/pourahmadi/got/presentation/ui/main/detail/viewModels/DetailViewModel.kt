package ir.pourahmadi.got.presentation.ui.main.detail.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pourahmadi.got.domain.common.base.BaseResult
import ir.pourahmadi.got.domain.common.error.ErrorEntity
import ir.pourahmadi.got.domain.model.HousesFounderCharacterModel
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.domain.use_case.AppUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: AppUseCase
) : ViewModel() {
    private val state = MutableStateFlow<DetailState>(DetailState.Init)
    val mState: StateFlow<DetailState> get() = state

    private fun setLoading() {
        state.value = DetailState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = DetailState.IsLoading(false)
    }


    fun getDetailOfHouse(detailUrl: String) {
        viewModelScope.launch {
            useCase.getDetailOfHouse(detailUrl)
                .onStart {
                    setLoading()
                }
                .collect {
                    hideLoading()
                    resultHandle(it)
                }
        }

    }
    fun getDetailOfHouseFounder(characterUrl: String) {
        viewModelScope.launch {
            useCase.getDetailOfHouseFounder(characterUrl)
                .onStart {
                    setLoading()
                }
                .collect {
                    hideLoading()
                    resultHandleFounder(it)
                }
        }

    }


    private fun resultHandle(result: BaseResult<HousesModel>) {
        when (result) {
            is BaseResult.Success -> {
                state.value = DetailState.Success(result.value)
            }
            is BaseResult.NetworkError -> state.value = handleError(result.error)
            is BaseResult.GeneralError -> state.value = DetailState.ShowResIdToast(result.redId)
        }
    }
    private fun resultHandleFounder(result: BaseResult<HousesFounderCharacterModel>) {
        when (result) {
            is BaseResult.Success -> {
                state.value = DetailState.SuccessFounder(result.value)
            }
            is BaseResult.NetworkError -> state.value = handleError(result.error)
            is BaseResult.GeneralError -> state.value = DetailState.ShowResIdToast(result.redId)
        }
    }

    private fun resultOfflineHandle(result: BaseResult<HousesModel>) {
        when (result) {
            is BaseResult.Success -> {
                state.value = DetailState.Success(result.value)
            }
            is BaseResult.NetworkError -> state.value = handleError(result.error)
            is BaseResult.GeneralError -> state.value = DetailState.ShowResIdToast(result.redId)
        }
    }

    private fun handleError(errorEntity: ErrorEntity): DetailState {
        return when (errorEntity) {
            is ErrorEntity.FromServer -> DetailState.ShowToast(
                errorEntity.error?.toString().toString()
            )
            is ErrorEntity.AccessDenied -> DetailState.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.Network -> DetailState.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.NotFound -> DetailState.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.ServiceUnavailable -> DetailState.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.Unknown -> DetailState.ShowResIdToast(errorEntity.redId)
        }
    }

}

sealed class DetailState {
    object Init : DetailState()
    data class IsLoading(val isLoading: Boolean) : DetailState()
    data class ShowToast(val message: String) : DetailState()
    data class Success(val mModel: HousesModel?) : DetailState()
    data class SuccessFounder(val mModel: HousesFounderCharacterModel?) : DetailState()
    data class ShowResIdToast(val resId: Int) : DetailState()
}