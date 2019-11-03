package co.waqas.paypaycodingchallenge.data.database.repository.currencies.list

import io.reactivex.Observable

interface ICurrencyListRepo {

    fun isRepoEmpty(): Boolean

    fun loadCurrencies(): List<CurrencyList>

    fun insertCurrencies(currencies: CurrencyList) : Observable<Boolean>
}