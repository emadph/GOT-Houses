package ir.pourahmadi.got.domain.use_case

import android.content.Context
import ir.pourahmadi.got.data.remote.dto.HousesRequest
import ir.pourahmadi.got.domain.common.base.BaseResult
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.domain.repository.AppRepository
import ir.pourahmadi.got.utils.Network.Network
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppUseCase @Inject constructor(
    private val context: Context,
    private val repository: AppRepository
) {
    suspend fun getHouses(request: HousesRequest): Flow<BaseResult<List<HousesModel>>> {
        return if (Network.isOnline(context)) {
            repository.getHouses(request)
        } else
            repository.getHousesOffline()
    }

    suspend fun getDetailOfHouse(detailUrl: String): Flow<BaseResult<HousesModel>> {
        return if (Network.isOnline(context)) {
            repository.getDetailOfHouses(detailUrl)
        } else
            repository.getDetailOfHousesOffline(detailUrl)
    }
}