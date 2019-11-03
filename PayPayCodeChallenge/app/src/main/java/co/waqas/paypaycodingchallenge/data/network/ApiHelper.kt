package co.waqas.paypaycodingchallenge.data.network

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.network.request.CurrencyListRequest
import co.waqas.paypaycodingchallenge.data.network.request.CurrencyRatesRequest
import co.waqas.paypaycodingchallenge.data.network.response.CurrencyListResponse
import co.waqas.paypaycodingchallenge.data.network.response.CurrencyRatesResponse
import co.waqas.paypaycodingchallenge.data.network.retrofit.ApiService
import co.waqas.paypaycodingchallenge.data.network.retrofit.RetrofitService
import co.waqas.paypaycodingchallenge.data.network.retrofit.RetrofitServiceModule
import co.waqas.paypaycodingchallenge.data.network.retrofit.RetrofitServiceModule_RetrofitServiceFactory
import co.waqas.paypaycodingchallenge.util.AppConstants
import co.waqas.paypaycodingchallenge.util.FileUtils
import com.google.gson.Gson
import io.reactivex.Observable
import javax.inject.Inject

class ApiHelper @Inject internal constructor(): IApiHelper {

    override fun getCurrencyList(context: Context): CurrencyList {
        return Gson().fromJson(FileUtils.readFileInStringFromRaw(context, AppConstants.FILE_CURRENCY_LIST), CurrencyList::class.java)
    }

    override fun getCurrencyRates(context: Context): CurrencyRates {
        return Gson().fromJson(FileUtils.readFileInStringFromRaw(context, AppConstants.FILE_EXCHANGE_RATES), CurrencyRates::class.java)
    }

    /**
     * This function call is to be made when data is required from server.
     * For testing and demonstration purposes, it is not being used here, instead, a normal
     * function to convert sample response to object is used
     */
    override fun fetchCurrencyList(currencyListRequest: CurrencyListRequest): Observable<CurrencyListResponse> {
        return RetrofitServiceModule_RetrofitServiceFactory
            .create(RetrofitServiceModule())
            .get()
            .getRetrofitObject()
            .create(ApiService::class.java)
            .fetchCurrencyList(currencyListRequest)
            .execute()
            .body()!!
    }

    override fun fetchCurrencyRates(currencyRatesRequest: CurrencyRatesRequest): Observable<CurrencyRatesResponse> {
        return RetrofitServiceModule_RetrofitServiceFactory
            .create(RetrofitServiceModule())
            .get()
            .getRetrofitObject()
            .create(ApiService::class.java)
            .fetchCurrencyRates(currencyRatesRequest)
            .execute()
            .body()!!
    }
}