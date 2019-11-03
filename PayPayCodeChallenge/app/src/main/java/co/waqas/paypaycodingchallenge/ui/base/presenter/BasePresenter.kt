package co.waqas.paypaycodingchallenge.ui.base.presenter

import co.waqas.paypaycodingchallenge.ui.base.interactor.IBaseInteractor
import co.waqas.paypaycodingchallenge.ui.base.view.IBaseView
import co.waqas.paypaycodingchallenge.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : IBaseView, I : IBaseInteractor> internal constructor(protected var interactor: I?, protected val schedulerProvider: SchedulerProvider, protected val compositeDisposable: CompositeDisposable) : IBasePresenter<V, I> {

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    override fun onAttach(view: V?) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
        interactor = null
    }

    override fun getView(): V? = view

}