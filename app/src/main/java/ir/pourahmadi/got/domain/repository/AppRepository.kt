package ir.pourahmadi.got.domain.repository

import ir.pourahmadi.got.data.remote.dto.HousesRequest
import ir.pourahmadi.got.domain.common.base.BaseResult
import ir.pourahmadi.got.domain.model.HousesModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getHouses(request: HousesRequest): Flow<BaseResult<List<HousesModel>>>
    suspend fun getHousesOffline(): Flow<BaseResult<List<HousesModel>>>

    suspend fun getDetailOfHouses(detailUrl: String): Flow<BaseResult<HousesModel>>
    suspend fun getDetailOfHousesOffline(detailUrl: String): Flow<BaseResult<HousesModel>>
}