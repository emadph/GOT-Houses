package ir.pourahmadi.got.data.remote.dto

import com.google.gson.annotations.SerializedName
import ir.pourahmadi.got.data.local.entity.HousesFounderCharacterEntity

data class HousesFounderCharacterBaseResponse(
    @SerializedName("url") var url: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("culture") var culture: String? = null,
    @SerializedName("titles") var titles: List<String>? = null

) {
    fun toHousesFounderCharacterEntityList(): List<HousesFounderCharacterEntity> {
        val list = mutableListOf<HousesFounderCharacterEntity>()
        list.add(
            HousesFounderCharacterEntity(
                url.toString(),
                name.toString(),
                gender.toString(),
                culture.toString(),
                titles!!
            )
        )
        return list
    }

    fun toHousesFounderCharacterEntity(): HousesFounderCharacterEntity {
        return HousesFounderCharacterEntity(
            url.toString(),
            name.toString(),
            gender.toString(),
            culture.toString(),
            titles!!
        )
    }


}