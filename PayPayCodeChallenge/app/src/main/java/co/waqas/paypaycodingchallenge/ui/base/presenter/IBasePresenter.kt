package co.waqas.paypaycodingchallenge.ui.base.presenter

import co.waqas.paypaycodingchallenge.ui.base.interactor.IBaseInteractor
import co.waqas.paypaycodingchallenge.ui.base.view.IBaseView

interface IBasePresenter<V : IBaseView, I : IBaseInteractor> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?
}