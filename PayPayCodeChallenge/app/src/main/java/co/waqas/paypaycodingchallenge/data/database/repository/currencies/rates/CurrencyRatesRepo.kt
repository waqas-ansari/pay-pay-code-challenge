package co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates

import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRatesRepo @Inject internal constructor(private val currencyDao: CurrencyRatesDao) : ICurrencyRatesRepo {

    override fun loadCurrencies2(): Observable<List<CurrencyRates>> {
        return Observable.fromCallable {
            currencyDao.loadAll()
        }
    }

    override fun isRepoEmpty(): Boolean = currencyDao.loadAll().isEmpty()

    override fun loadCurrencies(): List<CurrencyRates> {
        return currencyDao.loadAll()
    }


    override fun insertCurrencies(currencyRates: CurrencyRates): Observable<Boolean> {
        currencyDao.insertAll(currencyRates)
        return Observable.just(true)
    }

}