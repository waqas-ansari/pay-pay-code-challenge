package co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.waqas.paypaycodingchallenge.util.AppConstants

@Dao
interface CurrencyRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: CurrencyRates)

    @Query("SELECT * FROM ${AppConstants.DB_CURRENCY_RATES}")
    fun loadAll(): List<CurrencyRates>
}