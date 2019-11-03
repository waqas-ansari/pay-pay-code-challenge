package co.waqas.paypaycodingchallenge.data.network

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.network.request.CurrencyListRequest
import co.waqas.paypaycodingchallenge.data.network.request.CurrencyRatesRequest
import co.waqas.paypaycodingchallenge.data.network.response.CurrencyListResponse
import co.waqas.paypaycodingchallenge.data.network.response.CurrencyRatesResponse
import io.reactivex.Observable

interface IApiHelper {

    fun fetchCurrencyList(currencyListRequest: CurrencyListRequest): Observable<CurrencyListResponse>

    fun fetchCurrencyRates(currencyRatesRequest: CurrencyRatesRequest) : Observable<CurrencyRatesResponse>

    fun getCurrencyList(context: Context): CurrencyList

    fun getCurrencyRates(context: Context): CurrencyRates

}