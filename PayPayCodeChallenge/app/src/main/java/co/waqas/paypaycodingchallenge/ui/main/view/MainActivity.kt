package co.waqas.paypaycodingchallenge.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.waqas.paypaycodingchallenge.R
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.list.CurrencyList
import co.waqas.paypaycodingchallenge.data.model.CurrencyUnitRate
import co.waqas.paypaycodingchallenge.ui.base.view.BaseActivity
import co.waqas.paypaycodingchallenge.ui.main.adapter.ExchangeRatesAdapter
import co.waqas.paypaycodingchallenge.ui.main.interactor.IMainInteractor
import co.waqas.paypaycodingchallenge.ui.main.presenter.IMainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), IMainView {

    @Inject
    internal lateinit var presenter: IMainPresenter<IMainView, IMainInteractor>

    private lateinit var adapter: ExchangeRatesAdapter
    private lateinit var selectedCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onAttach(this)
        init()
    }

    fun init() {
        setAmountTextView()
        setAdapter()
        setSpinner()
    }

    fun setAdapter() {
        adapter = ExchangeRatesAdapter()

        exchangeRatesRecyclerView.layoutManager = GridLayoutManager(this, 3)
        exchangeRatesRecyclerView.adapter = adapter
    }

    fun setAmountTextView() {
        editTextEnterAmount.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty())
                    adapter.updateAmount(s.toString().toDouble())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun setSpinner() {
        presenter.loadCurrencyList(this)
    }

    override fun updateExchangeRates(unitCurrencyList: List<CurrencyUnitRate>) {
        runOnUiThread(Runnable {
            adapter.updateCurrencyList(unitCurrencyList)
        })
    }

    override fun updateCurrencyList(currencyList: CurrencyList) {
        val itemsList = currencyList.currencyList?.keys?.toList()
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemsList?.toTypedArray()!!)
        spinnerCurrencySelection.adapter = arrayAdapter
        spinnerCurrencySelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCurrency = itemsList[position]
                presenter.loadCurrencyRateList(this@MainActivity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        presenter.convertToUnitCurrencyRate(spinnerCurrencySelection.selectedItem as String)
    }

    override fun updateCurrencyRatesList() {
        presenter.convertToUnitCurrencyRate(selectedCurrency)
    }


}
