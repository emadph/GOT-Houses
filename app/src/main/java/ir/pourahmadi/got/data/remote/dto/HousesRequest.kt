package ir.pourahmadi.got.data.remote.dto

import com.google.gson.annotations.SerializedName
import ir.pourahmadi.got.utils.filterNotNullValues

data class HousesRequest(
    @SerializedName("page") var page: Int,
) {
    fun toRequest(): Map<String, Int> {
        val request: Map<String, Int?> = mapOf(
            "page" to page
        )
        return request.filterNotNullValues()
    }

}
