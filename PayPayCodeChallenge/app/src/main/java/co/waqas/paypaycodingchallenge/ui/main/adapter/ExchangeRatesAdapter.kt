package co.waqas.paypaycodingchallenge.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.waqas.paypaycodingchallenge.R
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import co.waqas.paypaycodingchallenge.data.model.CurrencyUnitRate
import kotlinx.android.synthetic.main.item_exchange_rates.view.*

class ExchangeRatesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val ITEM_TYPE_EMPTY = 1
        val ITEM_TYPE_VALUE = 2
    }

    private var unitCurrencyRates: List<CurrencyUnitRate> = ArrayList()
    private var amount: Double = 0.0

    fun updateAmount(amount: Double) {
        this.amount = amount
        notifyDataSetChanged()
    }

    fun updateCurrencyList(unitCurrencyRates: List<CurrencyUnitRate>) {
        this.unitCurrencyRates = unitCurrencyRates
        notifyDataSetChanged()
    }

    fun update(amount: Double, unitCurrencyRates: List<CurrencyUnitRate>) {
        this.amount = amount
        this.unitCurrencyRates = unitCurrencyRates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_EMPTY -> return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_rates, parent, false))
            ITEM_TYPE_VALUE -> return CurrencyRatesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_rates, parent, false))
        }
        return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_rates, parent, false))
    }

    override fun getItemCount(): Int {
        return unitCurrencyRates.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {
            holder.bind()
        } else if (holder is CurrencyRatesViewHolder) {
            holder.bind(amount, unitCurrencyRates[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (unitCurrencyRates.isEmpty()) {
            return ITEM_TYPE_EMPTY;
        } else return ITEM_TYPE_VALUE;
    }


    class CurrencyRatesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(amount: Double, unitRate: CurrencyUnitRate) {
            val conversion = String.format("%.3f", unitRate.unitRate*amount)
            view.textExchangeRates.text = "${unitRate.toCurrency}\n${conversion}"
        }

    }

    class EmptyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind() {
            view.textExchangeRates.text = view.context.getString(R.string.adapter_empty_text_no_exchange_rates_found)
        }

    }

}