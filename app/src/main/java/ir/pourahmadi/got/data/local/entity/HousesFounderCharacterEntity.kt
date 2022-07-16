package ir.pourahmadi.got.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.pourahmadi.got.domain.model.HousesFounderCharacterModel
import ir.pourahmadi.got.domain.model.HousesModel

@Entity
data class HousesFounderCharacterEntity(
    @PrimaryKey
    var url: String,
    var name: String,
    var gender: String,
    var culture: String,
    var titles: List<String>
) {
    fun toHousesFounderCharacterOfflineModel(): HousesFounderCharacterModel {
        return HousesFounderCharacterModel(
            url,
            name,
            gender,
            culture,
            titles
        )
    }
}
