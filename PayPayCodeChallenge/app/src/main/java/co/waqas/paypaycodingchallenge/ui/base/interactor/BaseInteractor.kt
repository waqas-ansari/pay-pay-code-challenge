package co.waqas.paypaycodingchallenge.ui.base.interactor

import co.waqas.paypaycodingchallenge.data.network.ApiHelper

open class BaseInteractor(): IBaseInteractor {
    protected lateinit var apiHelper: ApiHelper

    constructor(apiHelper: ApiHelper) : this() {
        this.apiHelper = apiHelper
    }
}