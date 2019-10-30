package co.waqas.paypaycodingchallenge.data.database

import androidx.room.Database
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.Currency
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.CurrencyDao


@Database(entities = [(Currency::class)], version = 1)
abstract class AppDatabase {

    abstract fun currencyDao(): CurrencyDao
}