package co.waqas.paypaycodingchallenge.data.network.retrofit

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitServiceModule {
    @Provides
    @Singleton
    fun retrofitService() : RetrofitService = RetrofitService()
}