package co.waqas.paypaycodingchallenge.di.component

import android.app.Application
import co.waqas.paypaycodingchallenge.CurrencyApp
import co.waqas.paypaycodingchallenge.data.network.retrofit.RetrofitService
import co.waqas.paypaycodingchallenge.data.network.retrofit.RetrofitServiceModule
import co.waqas.paypaycodingchallenge.di.builder.ActivityBuilder
import co.waqas.paypaycodingchallenge.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (RetrofitServiceModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {

    fun retrofitService(): RetrofitService

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: CurrencyApp)

}