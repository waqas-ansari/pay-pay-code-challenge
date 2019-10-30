package co.waqas.paypaycodingchallenge.data.database.repository.currencies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: List<Currency>)

    @Query("SELECT * FROM currencies")
    fun loadAll(): List<Currency>

}