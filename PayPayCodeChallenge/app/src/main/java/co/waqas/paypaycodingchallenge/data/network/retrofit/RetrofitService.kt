package co.waqas.paypaycodingchallenge.data.network.retrofit

import android.content.Context
import co.waqas.paypaycodingchallenge.data.network.ApiEndPoint
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitService {

    fun getRetrofitObject() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiEndPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(OkHttpClient())
            .build()
    }

}