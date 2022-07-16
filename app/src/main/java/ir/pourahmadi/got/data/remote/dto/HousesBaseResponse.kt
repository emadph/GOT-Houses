package ir.pourahmadi.got.data.remote.dto

import com.google.gson.annotations.SerializedName
import ir.pourahmadi.got.data.local.entity.HousesEntity

data class HousesBaseResponse(
    @SerializedName("url") var url: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("coatOfArms") var flagDesc: String? = null,
    @SerializedName("founded") var founded: String? = null,
    @SerializedName("founder") var founder: String? = null,
    @SerializedName("words") var words: String? = null,
    @SerializedName("titles") var titles: List<String>? = null

) {
    fun toHousesEntityList(): List<HousesEntity> {
        val list = mutableListOf<HousesEntity>()
        list.add(
            HousesEntity(
                url.toString(),
                name.toString(),
                region.toString(),
                flagDesc.toString(),
                words.toString(),
                founded.toString(),
                founder.toString(),
                titles!!
            )
        )
        return list
    }

    fun toHousesEntity(): HousesEntity {
        return HousesEntity(
            url.toString(),
            name.toString(),
            region.toString(),
            flagDesc.toString(),
            words.toString(),
            founded.toString(),
            founder.toString(),
            titles!!
        )
    }


}