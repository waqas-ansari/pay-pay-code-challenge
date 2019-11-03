package co.waqas.paypaycodingchallenge.ui.main

import co.waqas.paypaycodingchallenge.ui.main.interactor.IMainInteractor
import co.waqas.paypaycodingchallenge.ui.main.interactor.MainInteractor
import co.waqas.paypaycodingchallenge.ui.main.presenter.IMainPresenter
import co.waqas.paypaycodingchallenge.ui.main.presenter.MainPresenter
import co.waqas.paypaycodingchallenge.ui.main.view.IMainView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainInteractor(mainInteractor: MainInteractor): IMainInteractor = mainInteractor

    @Provides
    internal fun provideMainPresenter(mainPresenter: MainPresenter<IMainView, IMainInteractor>)
            : IMainPresenter<IMainView, IMainInteractor> = mainPresenter

}