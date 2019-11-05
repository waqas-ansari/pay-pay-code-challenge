package co.waqas.paypaycodingchallenge.data.database.repository.currencies.list

import io.reactivex.Observable
import javax.inject.Inject

class CurrencyListRepo @Inject internal constructor(private val currencyDao: CurrencyListDao) : ICurrencyListRepo {

    override fun isRepoEmpty2(): Observable<Boolean> {
        return Observable.fromCallable {
            currencyDao.loadAll().isEmpty()
        }
    }

    override fun isRepoEmpty(): Boolean = currencyDao.loadAll().isEmpty()

    override fun loadCurrencies(): List<CurrencyList> = currencyDao.loadAll()


    override fun insertCurrencies(currencies: CurrencyList): Observable<Boolean> {
        currencyDao.insertAll(currencies)
        return Observable.just(true)
    }

}