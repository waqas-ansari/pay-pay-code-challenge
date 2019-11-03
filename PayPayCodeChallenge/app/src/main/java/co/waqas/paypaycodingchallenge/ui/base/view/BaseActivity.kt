package co.waqas.paypaycodingchallenge.ui.base.view

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.waqas.paypaycodingchallenge.util.CommonUtils
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity(), IBaseView {

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

    override fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun showProgress() {
        hideProgress()
        progressDialog = CommonUtils.showLoadingDialog(this)
    }

    private fun performDI() = AndroidInjection.inject(this)

}
