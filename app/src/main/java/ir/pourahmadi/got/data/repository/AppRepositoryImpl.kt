package ir.pourahmadi.got.data.repository

import ir.pourahmadi.got.data.local.dao.HousesDao
import ir.pourahmadi.got.data.local.entity.HousesEntity
import ir.pourahmadi.got.data.local.entity.HousesFounderCharacterEntity
import ir.pourahmadi.got.data.remote.api.AppApi
import ir.pourahmadi.got.data.remote.dto.HousesBaseResponse
import ir.pourahmadi.got.data.remote.dto.HousesFounderCharacterBaseResponse
import ir.pourahmadi.got.data.remote.dto.HousesRequest
import ir.pourahmadi.got.domain.common.base.BaseResult
import ir.pourahmadi.got.domain.common.error.ErrorHandler
import ir.pourahmadi.got.domain.model.HousesFounderCharacterModel
import ir.pourahmadi.got.domain.model.HousesModel
import ir.pourahmadi.got.domain.repository.AppRepository
import ir.pourahmadi.got.utils.safeCall
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val errorHandler: ErrorHandler,
    private val dao: HousesDao
) : AppRepository {

    override suspend fun getHouses(request: HousesRequest): Flow<BaseResult<List<HousesModel>>> {
        return safeCall(errorHandler) {
            val response = api.getHouses(request.toRequest())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { bodyResponse ->
                    try {
                        for (item in bodyResponse) {
                            dao.insertHousesData(generateCache(item))
                        }
                    } catch (e: Exception) {
                    }

                    BaseResult.Success(generateOfflineModel())
                } ?: run {
                    BaseResult.GeneralError()
                }
            } else {
                throw HttpException(response)
            }
        }

    }

    override suspend fun getDetailOfHouses(detailUrl: String): Flow<BaseResult<HousesModel>> {
        return safeCall(errorHandler) {
            val response = api.getDetailHouses(detailUrl)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { bodyResponse ->
                    try {
                        dao.insertDetailOfHousesData(generateCacheDetailOfHouses(bodyResponse))
                    } catch (e: Exception) {
                    }

                    BaseResult.Success(generateOfflineDetailHousesModel(detailUrl))
                } ?: run {
                    BaseResult.GeneralError()
                }
            } else {
                throw HttpException(response)
            }
        }

    }
    override suspend fun getDetailOfHousesFounder(characterUrl: String): Flow<BaseResult<HousesFounderCharacterModel>> {
        return safeCall(errorHandler) {
            val response = api.getDetailHousesFounder(characterUrl)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { bodyResponse ->
                    try {
                        dao.insertDetailOfHousesFounderData(generateCacheDetailOfHousesFounder(bodyResponse))
                    } catch (e: Exception) {
                    }
                    BaseResult.Success(generateOfflineDetailHousesFounderCharacterModel(characterUrl))
                } ?: run {
                    BaseResult.GeneralError()
                }
            } else {
                throw HttpException(response)
            }
        }

    }
    override suspend fun getHousesOffline(): Flow<BaseResult<List<HousesModel>>> {
        return safeCall(errorHandler) {
            BaseResult.Success(generateOfflineModel())
        }
    }
    override suspend fun getDetailOfHousesFounderOffline(characterUrl: String): Flow<BaseResult<HousesFounderCharacterModel>> {
        return safeCall(errorHandler) {
            BaseResult.Success(generateOfflineDetailHousesFounderCharacterModel(characterUrl))
        }
    }

    override suspend fun getDetailOfHousesOffline(detailUrl: String): Flow<BaseResult<HousesModel>> {
        return safeCall(errorHandler) {
            BaseResult.Success(generateOfflineDetailHousesModel(detailUrl))
        }

    }

    private suspend fun generateOfflineModel(): List<HousesModel>? {
        val response = dao.getHouses()
        response?.let {
            return responseToOfflineModel(response)
        } ?: return null
    }

    private suspend fun generateOfflineDetailHousesModel(detailUrl: String): HousesModel? {
        val response = dao.getDetailOfHouses(detailUrl)
        response?.let {
            return responseToOfflineDetailOfHousesModel(response)
        } ?: return null
    }

    private suspend fun generateOfflineDetailHousesFounderCharacterModel(detailUrl: String): HousesFounderCharacterModel? {
        val response = dao.getDetailOfHousesFounderCharacter(detailUrl)
        response?.let {
            return responseToOfflineDetailOfHousesFounderModel(response)
        } ?: return null
    }

    private fun generateCache(
        responseList: HousesBaseResponse,
    ): List<HousesEntity> {
        return responseList.toHousesEntityList()
    }

    private fun generateCacheDetailOfHouses(
        responseList: HousesBaseResponse,
    ): HousesEntity {
        return responseList.toHousesEntity()
    }
    private fun generateCacheDetailOfHousesFounder(
        responseList: HousesFounderCharacterBaseResponse,
    ): HousesFounderCharacterEntity {
        return responseList.toHousesFounderCharacterEntity()
    }

    private fun responseToOfflineModel(cacheEntity: List<HousesEntity>): List<HousesModel> {
        return cacheEntity.map { it.toHousesOfflineModel() }
    }

    private fun responseToOfflineDetailOfHousesModel(cacheEntity: HousesEntity): HousesModel {
        return cacheEntity.toHousesOfflineModel()
    }
    private fun responseToOfflineDetailOfHousesFounderModel(cacheEntity: HousesFounderCharacterEntity): HousesFounderCharacterModel {
        return cacheEntity.toHousesFounderCharacterOfflineModel()
    }

}