package co.waqas.paypaycodingchallenge.data.database.repository.currencies.list

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.waqas.paypaycodingchallenge.util.AppConstants

@Dao
interface CurrencyListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: CurrencyList)

    @Query("SELECT * FROM ${AppConstants.DB_CURRENCY_LIST}")
    fun loadAll(): List<CurrencyList>

}