package ir.pourahmadi.got.data.remote.api

import ir.pourahmadi.got.data.remote.dto.HousesBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface AppApi {

    @GET("houses")
    suspend fun getHouses(@QueryMap(encoded = true) data: Map<String, Int>): Response<List<HousesBaseResponse>>

    @GET
    suspend fun getDetailHouses(@Url url: String): Response<HousesBaseResponse>

}