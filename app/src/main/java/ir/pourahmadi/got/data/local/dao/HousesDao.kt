package ir.pourahmadi.got.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.pourahmadi.got.data.local.entity.HousesEntity

@Dao
interface HousesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHousesData(entity: List<HousesEntity>)

    @Query("DELETE FROM HousesEntity")
    suspend fun dropHousesData()

    @Query("SELECT * FROM HousesEntity")
    suspend fun getHouses(): List<HousesEntity>?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailOfHousesData(entity: HousesEntity)

    @Query("DELETE FROM HousesEntity")
    suspend fun dropDetailOfHousesData()

    @Query("SELECT * FROM HousesEntity WHERE url = :url")
    suspend fun getDetailOfHouses(url: String): HousesEntity?

}