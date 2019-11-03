package co.waqas.paypaycodingchallenge.data.database.repository.currencies.rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.waqas.paypaycodingchallenge.util.AppConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.HashMap

/**
 * The data class represents the database table of currency rates
 */
@Entity(tableName = AppConstants.DB_CURRENCY_RATES)
data class CurrencyRates (

    @Expose
    @PrimaryKey
    var id: Long,

    @Expose
    @SerializedName("success")
    @ColumnInfo(name = "success")
    var success: Boolean,

    @Expose
    @SerializedName("terms")
    @ColumnInfo(name = "terms")
    var terms: String?,

    @Expose
    @SerializedName("privacy")
    @ColumnInfo(name = "privacy")
    var privacy: String?,

    @Expose
    @SerializedName("historical")
    @ColumnInfo(name = "historical")
    var historical: Boolean,

    @Expose
    @SerializedName("date")
    @ColumnInfo(name = "date")
    var date: String?,

    @Expose
    @SerializedName("timestamp")
    @ColumnInfo(name = "timestamp")
    var timestamp: Long?,

    @Expose
    @SerializedName("source")
    @ColumnInfo(name = "source")
    var source: String?,

    @Expose
    @SerializedName("quotes")
    @ColumnInfo(name = "quotes")
    //Key represents currencies short name while value is the exchange rate
    var quotes: HashMap<String, Double>

)