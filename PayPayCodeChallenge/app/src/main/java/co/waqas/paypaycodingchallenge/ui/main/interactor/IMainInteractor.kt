package co.waqas.paypaycodingchallenge.ui.main.interactor

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.ui.base.interactor.IBaseInteractor
import io.reactivex.Observable
import io.reactivex.Single

interface IMainInteractor: IBaseInteractor {

    fun isCurrencyListRepoEmpty(): Boolean

    fun isCurrencyListRepoEmpty2(): Observable<Boolean>

    fun loadCurrencyList(): CurrencyList

    fun loadCurrencyList2(): Observable<List<CurrencyList>>

    fun isCurrencyRatesRepoEmpty(): Boolean

    fun loadCurrencyRates(): CurrencyRates

    fun loadCurrencyRates2(): Observable<List<CurrencyRates>>

    fun fetchCurrencyList(context: Context): Observable<CurrencyList>

    fun fetchCurrencyRatesList(context: Context): Observable<CurrencyRates>

    fun insertCurrencyRate(currencyRates: CurrencyRates)

    fun insertCurrencyRate2(currencyRates: CurrencyRates): Observable<Boolean>

    fun insertCurrencyList(currencyList: CurrencyList)
}