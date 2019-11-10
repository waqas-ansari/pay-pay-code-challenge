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

    /**
     * Purpose of this function is to check if currency list repo and currency rates repo
     * is empty and based on that, either fetch data from the server or get from the DB
     */
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


    /**
     * Check is the currency rate repo is empty.
     */
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


    /**
     * Base on the result of loadCurrencyRateList and checkIfCurrencyRateIsEmpty, it checks if
     * 30 minutes has been passed or either of the repo's are empty, it fetches data from DB
     * or get it from server (json file in our case)
     */
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


    /**
     * This is responsible for checking if repo is empty or 30 mins has been passed since the last fetch and
     * then either gets it from DB or fetch from server
     */
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

    /**
     * It either fetches data from DB or Server and sends call to update UI
     */
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


    /**
     * Loads currency list from DB and sends UI update request to Voew
     */
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


    /**
     * It checks if 30 mins has been passed since the last fetch
     */
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


    /**
     * It inserts the currency rate list data into DB
     */
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


    /**
     * This function is responsible for converting unit currency rate to
     * all available currencies in DB
     */
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