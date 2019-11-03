package co.waqas.paypaycodingchallenge.util

import co.waqas.paypaycodingchallenge.di.component.AppComponent

enum class Injector {
    INSTANCE;

    private var appComponent: AppComponent? = null

    companion object {

        fun setComponent(appComponent: AppComponent) {
            INSTANCE.appComponent = appComponent
        }

        fun get(): AppComponent? {
            return INSTANCE.appComponent
        }
    }
}