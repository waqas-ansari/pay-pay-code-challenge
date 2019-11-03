package co.waqas.paypaycodingchallenge.data.database.repository.currencies.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.waqas.paypaycodingchallenge.util.AppConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.HashMap

/**
 * The data class represents the database table of currency list
 */
@Entity(tableName = AppConstants.DB_CURRENCY_LIST)
data class CurrencyList (
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
    @SerializedName("currencies")
    @ColumnInfo(name = "currencies")
    //Key represents currencies short name while value is long name
    var currencyList: HashMap<String, String>?

)