package co.waqas.paypaycodingchallenge.ui.main.view

import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.model.CurrencyUnitRate
import co.waqas.paypaycodingchallenge.ui.base.view.IBaseView

interface IMainView : IBaseView {

    fun updateExchangeRates(unitCurrencyList: List<CurrencyUnitRate>)

    fun updateCurrencyList(currencyList: CurrencyList)

    fun updateCurrencyRatesList()

}