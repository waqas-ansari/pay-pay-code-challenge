package co.waqas.paypaycodingchallenge.ui.main.interactor

import co.waqas.paypaycodingchallenge.data.database.repository.currencies.Currency
import co.waqas.paypaycodingchallenge.ui.base.interactor.IBaseInteractor
import io.reactivex.Observable
import io.reactivex.Single

interface IMainInteractor: IBaseInteractor {

    fun isCurrencyRepoEmpty(): Observable<Boolean>

    fun loadAllCurrencies() : Single<MutableList<List<Currency>>>?
}