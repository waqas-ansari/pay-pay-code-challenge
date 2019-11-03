package co.waqas.paypaycodingchallenge.data.network.retrofit

import co.waqas.paypaycodingchallenge.data.network.ApiEndPoint
import co.waqas.paypaycodingchallenge.data.network.request.CurrencyListRequest
import co.waqas.paypaycodingchallenge.data.network.request.CurrencyRatesRequest
import co.waqas.paypaycodingchallenge.data.network.response.CurrencyListResponse
import co.waqas.paypaycodingchallenge.data.network.response.CurrencyRatesResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(ApiEndPoint.FETCH_CURRENCY_LIST)
    fun fetchCurrencyList(currencyListRequest: CurrencyListRequest) : Call<Observable<CurrencyListResponse>>

    @GET(ApiEndPoint.FETCH_CURRENCY_RATES)
    fun fetchCurrencyRates(currencyRatesRequest: CurrencyRatesRequest) : Call<Observable<CurrencyRatesResponse>>

}