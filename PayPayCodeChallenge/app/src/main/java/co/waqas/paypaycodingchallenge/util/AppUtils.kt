package co.waqas.paypaycodingchallenge.util

import android.content.Context
import co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates.CurrencyRates
import org.json.JSONObject

object AppUtils {

    object CurrencyConverterUtils {

        fun getExchangeRate(from: String, to: String, currencyRates: CurrencyRates): Double {
            val ratesMap = currencyRates.quotes
            if (ratesMap.containsKey("$from$to") || ratesMap.containsKey("$to$from")) {
                if (ratesMap.containsKey("$from$to")) {
                    return ratesMap["$from$to"]!!
                } else {
                    return (1/ratesMap["$to$from"]!!)
                }
            } else {
                if (ratesMap.containsKey("${currencyRates.source}$from") && ratesMap.containsKey("${currencyRates.source}$to")) {
                    val rateSourceFrom = ratesMap["${currencyRates.source}$from"]!!
                    val rateSourceTo = ratesMap["${currencyRates.source}$to"]!!
                    return rateSourceTo/rateSourceFrom
                }
            }

            throw NoSuchFieldException("No exchange rates found for selected currency")
        }


        fun convert(context: Context, from: String, to: String, amount: Double, currencyRates: CurrencyRates): Double {
            return (amount * getExchangeRate(from, to, currencyRates))
        }


        fun getFrom(toAndFromCurrency: String): String {
            return toAndFromCurrency.substring(0, 2)
        }

        fun getTo(toAndFromCurrency: String): String {
            return toAndFromCurrency.substring(3, toAndFromCurrency.length)
        }

    }

}