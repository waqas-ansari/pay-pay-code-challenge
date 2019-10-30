package co.waqas.paypaycodingchallenge.data.database.repository.currencies

import io.reactivex.Observable

interface ICurrencyRepo {

    fun isRepoEmpty(): Observable<Boolean>

    fun loadCurrencies(): Observable<List<Currency>>

    fun insertCurrencies(currencies: List<Currency>) : Observable<Boolean>

}