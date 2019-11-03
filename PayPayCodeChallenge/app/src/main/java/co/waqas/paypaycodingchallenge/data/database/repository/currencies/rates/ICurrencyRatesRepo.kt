package co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates

import io.reactivex.Observable

interface ICurrencyRatesRepo {

    fun isRepoEmpty(): Boolean

    fun loadCurrencies(): List<CurrencyRates>

    fun insertCurrencies(currencyRates: CurrencyRates) : Observable<Boolean>

}