package co.waqas.paypaycodingchallenge.ui.main.interactor

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.ui.base.interactor.IBaseInteractor
import io.reactivex.Observable
import io.reactivex.Single

interface IMainInteractor: IBaseInteractor {

    fun isCurrencyListRepoEmpty(): Boolean

    fun loadCurrencyList(): CurrencyList

    fun isCurrencyRatesRepoEmpty(): Boolean

    fun loadCurrencyRates(): CurrencyRates

    fun fetchCurrencyList(context: Context): Observable<CurrencyList>

    fun fetchCurrencyRatesList(context: Context): Observable<CurrencyRates>

    fun insertCurrencyRate(currencyRates: CurrencyRates)

    fun insertCurrencyList(currencyList: CurrencyList)
}