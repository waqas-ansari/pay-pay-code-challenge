package co.waqas.paypaycodingchallenge.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyListResponse(@Expose
                                @SerializedName("dummy")
                                var dummy: String? = null)