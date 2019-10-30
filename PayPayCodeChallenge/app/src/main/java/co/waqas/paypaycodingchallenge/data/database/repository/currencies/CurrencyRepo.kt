package co.waqas.paypaycodingchallenge.data.database.repository.currencies

import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRepo @Inject internal constructor(private val currencyDao: CurrencyDao) : ICurrencyRepo {

    override fun isRepoEmpty(): Observable<Boolean> = Observable.fromCallable {
        currencyDao.loadAll().isEmpty()
    }

    override fun loadCurrencies(): Observable<List<Currency>> = Observable.fromCallable {
        currencyDao.loadAll()
    }


    override fun insertCurrencies(currencies: List<Currency>): Observable<Boolean> {
        currencyDao.insertAll(currencies)
        return Observable.just(true)
    }
}