package co.waqas.paypaycodingchallenge.ui.main.presenter

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.model.CurrencyUnitRate
import co.waqas.paypaycodingchallenge.ui.base.presenter.BasePresenter
import co.waqas.paypaycodingchallenge.ui.main.interactor.IMainInteractor
import co.waqas.paypaycodingchallenge.ui.main.view.IMainView
import co.waqas.paypaycodingchallenge.util.AppConstants
import co.waqas.paypaycodingchallenge.util.AppUtils
import co.waqas.paypaycodingchallenge.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter<V : IMainView, I : IMainInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), IMainPresenter<V, I> {

    override fun loadCurrencyList(context: Context) {
        interactor?.let {
            compositeDisposable.add(
                it.isCurrencyListRepoEmpty2()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe { isEmpty ->
                        this.checkIfCurrencyRateIsEmpty(isEmpty, context)
                    }
            )
        }
    }

    private fun checkIfCurrencyRateIsEmpty(isCurrencyListRepoEmpty: Boolean, context: Context) {
        interactor?.let {
            compositeDisposable.add(
                it.loadCurrencyRates2()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe { currencyRatesList ->
                        this.checkIfForceFetchAndLoadCurrencyList(isCurrencyListRepoEmpty, currencyRatesList, context)
                    }
            )
        }
    }


    private fun checkIfForceFetchAndLoadCurrencyList(isRepoEmpty: Boolean, currencyRatesList: List<CurrencyRates>, context: Context) {
        if (isRepoEmpty || forceFetchCurrencyList(currencyRatesList)) {
            interactor?.let { it ->
                compositeDisposable.add(
                    it.fetchCurrencyRatesList(context)
                        .compose(schedulerProvider.ioToMainObservableScheduler())
                        .subscribe { currencyRates: CurrencyRates ->
                            getView()?.let {
                                interactor?.insertCurrencyRate(currencyRates)
                                loadCurrencyList(isRepoEmpty, context)
                            }
                        }
                )
            }
        } else {
            loadCurrencyList(isRepoEmpty, context)
        }
    }


    override fun loadCurrencyRateList(context: Context) {
        Thread(Runnable {
            if (interactor?.isCurrencyRatesRepoEmpty()!! || forceFetchCurrencyList(ArrayList())) {
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

    private fun loadCurrencyList(isCurrencyListRepoEmpty: Boolean, context: Context) {
        if (isCurrencyListRepoEmpty) {
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
            loadCurrencyListFromDb()
        }
    }

    private fun loadCurrencyListFromDb() {
        interactor?.let {
            compositeDisposable.add(
                it.loadCurrencyList2()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe { currencyList ->
                        getView()?.updateCurrencyList(currencyList[0])
                    }
            )
        }
    }

    private fun forceFetchCurrencyList(currencyRatesList: List<CurrencyRates>): Boolean {
        if (currencyRatesList.isEmpty()) return false
        val currencyRates = currencyRatesList[0]
        val diff = System.currentTimeMillis() - currencyRates.timestamp!!
        if (diff > AppConstants.DEVICE_CACHE_EXPIRY) {
            currencyRates.timestamp = System.currentTimeMillis()
            updateCurrencyRateList(currencyRates)
            return true
        }
        return false
    }

    private fun updateCurrencyRateList(currencyRates: CurrencyRates) {
        Thread(Runnable {
            interactor?.let {
                compositeDisposable.add(
                    it.insertCurrencyRate2(currencyRates)
                        .compose(schedulerProvider.ioToMainObservableScheduler())
                        .subscribe { isSuccessful ->
                            return@subscribe
                        }
                )
            }
        }).start()
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

}