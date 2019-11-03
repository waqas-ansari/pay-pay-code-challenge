package co.waqas.paypaycodingchallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyListDao
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRatesDao


@Database(entities = [(CurrencyList::class), (CurrencyRates::class)], version = 1)
@TypeConverters(MapTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyListDao(): CurrencyListDao

    abstract fun currencyRatesDao(): CurrencyRatesDao
}