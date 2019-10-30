package co.waqas.paypaycodingchallenge.ui.base.interactor

import co.waqas.paypaycodingchallenge.data.database.repository.currencies.CurrencyRepo
import co.waqas.paypaycodingchallenge.data.network.ApiHelper

class BaseInteractor(): IBaseInteractor {
    protected lateinit var apiHelper: ApiHelper

    constructor(apiHelper: ApiHelper) : this() {
        this.apiHelper = apiHelper
    }
}