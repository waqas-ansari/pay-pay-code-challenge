package co.waqas.paypaycodingchallenge.ui.main.interactor

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyListRepo
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRatesRepo
import co.waqas.paypaycodingchallenge.data.network.ApiHelper
import co.waqas.paypaycodingchallenge.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject internal constructor(private val currencyListRepo: CurrencyListRepo,
                                                  private val currencyRatesRepo: CurrencyRatesRepo,
                                                  apiHelper: ApiHelper) : BaseInteractor(apiHelper), IMainInteractor {
    override fun loadCurrencyList2(): Observable<List<CurrencyList>> {
        return currencyListRepo.loadCurrencies2()
    }

    override fun insertCurrencyRate2(currencyRates: CurrencyRates): Observable<Boolean> {
        return currencyRatesRepo.insertCurrencies(currencyRates)
    }

    override fun loadCurrencyRates2(): Observable<List<CurrencyRates>> {
        return currencyRatesRepo.loadCurrencies2()
    }


    override fun isCurrencyListRepoEmpty2(): Observable<Boolean> {
        return currencyListRepo.isRepoEmpty2()
    }

    override fun insertCurrencyRate(currencyRates: CurrencyRates) {
        Thread(Runnable {
            currencyRatesRepo.insertCurrencies(currencyRates)
        }).start()
    }

    override fun insertCurrencyList(currencyList: CurrencyList) {
        Thread(Runnable {
            currencyListRepo.insertCurrencies(currencyList)
        }).start()
    }

    /**
     * this is for demonstration purposes
     */
    override fun fetchCurrencyList(context: Context): Observable<CurrencyList> {
        return Observable.fromCallable {
            apiHelper.getCurrencyList(context)
        }
    }


    /**
     * this is for demonstration purposes
     */
    override fun fetchCurrencyRatesList(context: Context): Observable<CurrencyRates> {
        return Observable.fromCallable {
            apiHelper.getCurrencyRates(context)
        }
    }


    override fun isCurrencyListRepoEmpty(): Boolean = currencyListRepo.isRepoEmpty()

    override fun loadCurrencyList(): CurrencyList {
        return currencyListRepo.loadCurrencies()[0]
    }

    override fun isCurrencyRatesRepoEmpty(): Boolean = currencyRatesRepo.isRepoEmpty()

    override fun loadCurrencyRates(): CurrencyRates {
        return currencyRatesRepo.loadCurrencies()[0]
    }

}