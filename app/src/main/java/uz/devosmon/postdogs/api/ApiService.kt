package uz.devosmon.postdogs.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    var retrofit: Retrofit? = null

    fun apiClient(): Api {
        if (retrofit == null) {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(httpLoggingInterceptor)

           // https://dummyapi.io/data/api/
            retrofit = Retrofit.Builder()
                .baseUrl("https://dummyapi.io/data/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
        }
        return retrofit!!.create(Api::class.java)


    }
}