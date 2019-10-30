package co.waqas.paypaycodingchallenge.ui.main.interactor

import co.waqas.paypaycodingchallenge.data.database.repository.currencies.Currency
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.CurrencyRepo
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject internal constructor(private val currencyRepo: CurrencyRepo) : IMainInteractor {

    override fun isCurrencyRepoEmpty(): Observable<Boolean> = currencyRepo.isRepoEmpty()

    override fun loadAllCurrencies(): Single<MutableList<List<Currency>>>? = currencyRepo.loadCurrencies().toList()
}