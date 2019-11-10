package co.waqas.paypaycodingchallenge.ui.main.presenter

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.model.CurrencyUnitRate
import co.waqas.paypaycodingchallenge.ui.base.presenter.IBasePresenter
import co.waqas.paypaycodingchallenge.ui.main.interactor.IMainInteractor
import co.waqas.paypaycodingchallenge.ui.main.view.IMainView

interface IMainPresenter<V : IMainView, I : IMainInteractor> : IBasePresenter<V, I> {

    fun convertToUnitCurrencyRate(fromCurrency: String)

    fun loadCurrencyList(context: Context)

    fun loadCurrencyRateList(context: Context)

}