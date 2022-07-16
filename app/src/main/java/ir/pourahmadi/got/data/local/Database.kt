package ir.pourahmadi.got.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.pourahmadi.got.data.local.dao.HousesDao
import ir.pourahmadi.got.data.local.entity.HousesEntity
import ir.pourahmadi.got.data.local.entity.HousesFounderCharacterEntity

@Database(
    entities = [
        HousesEntity::class,
        HousesFounderCharacterEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract val Dao: HousesDao
}