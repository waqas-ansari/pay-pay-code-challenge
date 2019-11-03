package co.waqas.paypaycodingchallenge.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import co.waqas.paypaycodingchallenge.data.database.AppDatabase
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyListRepo
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRatesRepo
import co.waqas.paypaycodingchallenge.data.network.ApiHelper
import co.waqas.paypaycodingchallenge.data.network.IApiHelper
import co.waqas.paypaycodingchallenge.util.AppConstants
import co.waqas.paypaycodingchallenge.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.APP_DB_NAME).build()


    @Provides
    @Singleton
    internal fun provideApiHelper(): IApiHelper = ApiHelper()

    @Provides
    @Singleton
    internal fun provideCurrencyListRepoHelper(appDatabase: AppDatabase): CurrencyListRepo = CurrencyListRepo(appDatabase.currencyListDao())

    @Provides
    @Singleton
    internal fun provideCurrencyRatesRepoHelper(appDatabase: AppDatabase): CurrencyRatesRepo = CurrencyRatesRepo(appDatabase.currencyRatesDao())

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()

}