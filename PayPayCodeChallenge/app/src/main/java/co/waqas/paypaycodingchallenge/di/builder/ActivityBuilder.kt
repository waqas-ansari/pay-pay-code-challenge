package co.waqas.paypaycodingchallenge.di.builder

import co.waqas.paypaycodingchallenge.ui.main.MainActivityModule
import co.waqas.paypaycodingchallenge.ui.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

}