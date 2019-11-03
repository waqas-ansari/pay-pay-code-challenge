package co.waqas.paypaycodingchallenge.ui.main.presenter

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.model.CurrencyUnitRate
import co.waqas.paypaycodingchallenge.ui.base.presenter.BasePresenter
import co.waqas.paypaycodingchallenge.ui.main.interactor.IMainInteractor
import co.waqas.paypaycodingchallenge.ui.main.view.IMainView
import co.waqas.paypaycodingchallenge.util.AppUtils
import co.waqas.paypaycodingchallenge.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter<V : IMainView, I : IMainInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), IMainPresenter<V, I> {

    override fun loadCurrencyRateList(context: Context) {
        Thread(Runnable {
            if (interactor?.isCurrencyRatesRepoEmpty()!! || forceFetchCurrencyList()) {
                interactor?.let {
                    compositeDisposable.add(
                        it.fetchCurrencyRatesList(context)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe { currencyRates: CurrencyRates ->
                                getView()?.let {
                                    interactor?.insertCurrencyRate(currencyRates)
                                    it.updateCurrencyRatesList()
                                }
                            }
                    )
                }
            } else {
                getView()?.updateCurrencyRatesList()
            }
        }).start()
    }

    override fun loadCurrencyList(context: Context) {
        Thread(Runnable {
            if (interactor?.isCurrencyListRepoEmpty()!!) {
                interactor?.let {
                    compositeDisposable.add(
                        it.fetchCurrencyList(context)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe { currencyList: CurrencyList ->
                                getView()?.let {
                                    interactor?.insertCurrencyList(currencyList)
                                    it.updateCurrencyList(currencyList)
                                }
                            }
                    )
                }
            } else {
                getView()?.updateCurrencyList(interactor?.loadCurrencyList()!!)
            }
        }).start()
    }

    private fun forceFetchCurrencyList(): Boolean {
        val currencyRates = interactor?.loadCurrencyRates()
        val diff = System.currentTimeMillis() - currencyRates?.timestamp!!
        if (diff > 30*60000) {
            currencyRates.timestamp = System.currentTimeMillis()
            interactor?.insertCurrencyRate(currencyRates)
            return true
        }
        return false
    }

    override fun convertToUnitCurrencyRate(fromCurrency: String) {
        Thread(Runnable {
            val toCurrencyList: ArrayList<String> = ArrayList()
            val unitRateList: ArrayList<CurrencyUnitRate> = ArrayList()

            val currencyList = interactor?.loadCurrencyList()
            val currencyRates = interactor?.loadCurrencyRates()
            currencyList?.currencyList?.keys?.toCollection(toCurrencyList)
            for (toCurrency in toCurrencyList) {
                if (toCurrency == fromCurrency) continue
                unitRateList.add(CurrencyUnitRate(toCurrency,
                    AppUtils.CurrencyConverterUtils.getExchangeRate(fromCurrency, toCurrency, currencyRates!!)))
            }
            getView()?.updateExchangeRates(unitRateList)
        }).start()
    }


    override fun onAttach(view: V?) {
        super.onAttach(view)
    }

    override fun convertCurrency() {
    }

    override fun fetchCurrency() {
    }
}