package ir.pourahmadi.got.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.pourahmadi.got.domain.model.HousesModel

@Entity
data class HousesEntity(
    @PrimaryKey
    var url: String,
    var name: String,
    var region: String,
    var flagDesc: String,
    var words: String,
    var founded: String,
    var titles: List<String>
) {
    fun toHousesOfflineModel(): HousesModel {
        return HousesModel(
            url,
            name,
            region,
            flagDesc,
            words,
            founded,
            titles
        )
    }
}
