package ir.pourahmadi.got.presentation.ui.main.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pourahmadi.got.data.remote.dto.HousesRequest
import ir.pourahmadi.got.domain.common.base.BaseResult
import ir.pourahmadi.got.domain.common.error.ErrorEntity
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.domain.use_case.AppUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: AppUseCase
) : ViewModel() {
    private val state = MutableStateFlow<State>(State.Init)
    val mState: StateFlow<State> get() = state

    private fun setLoading() {
        state.value = State.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = State.IsLoading(false)
    }


    fun getHouses(request: HousesRequest) {
        viewModelScope.launch {
            useCase.getHouses(request)
                .onStart {
                    setLoading()
                }
                .collect {
                    hideLoading()
                    resultHandle(it)
                }
        }

    }


    private fun resultHandle(result: BaseResult<List<HousesModel>>) {
        when (result) {
            is BaseResult.Success -> {
                state.value = State.Success(result.value)
            }
            is BaseResult.NetworkError -> state.value = handleError(result.error)
            is BaseResult.GeneralError -> state.value = State.ShowResIdToast(result.redId)
        }
    }

    private fun resultOfflineHandle(result: BaseResult<List<HousesModel>>) {
        when (result) {
            is BaseResult.Success -> {
                state.value = State.Success(result.value)
            }
            is BaseResult.NetworkError -> state.value = handleError(result.error)
            is BaseResult.GeneralError -> state.value = State.ShowResIdToast(result.redId)
        }
    }

    private fun handleError(errorEntity: ErrorEntity): State {
        return when (errorEntity) {
            is ErrorEntity.FromServer -> State.ShowToast(errorEntity.error?.toString().toString())
            is ErrorEntity.AccessDenied -> State.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.Network -> State.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.NotFound -> State.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.ServiceUnavailable -> State.ShowResIdToast(errorEntity.redId)
            is ErrorEntity.Unknown -> State.ShowResIdToast(errorEntity.redId)
        }
    }

}

sealed class State {
    object Init : State()
    data class IsLoading(val isLoading: Boolean) : State()
    data class ShowToast(val message: String) : State()
    data class Success(val mModel: List<HousesModel>?) : State()
    data class ShowResIdToast(val resId: Int) : State()
}