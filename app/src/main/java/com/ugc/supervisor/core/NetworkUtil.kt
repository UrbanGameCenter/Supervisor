package com.ugc.supervisor.core

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkUtil {

    companion object{

        fun verifyAvailableNetwork(context: Context):Boolean{

            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo= connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }



        fun getRetrofit() : Retrofit {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client =
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .callTimeout(10000, TimeUnit.MILLISECONDS)
                    .build()


            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit
        }
    }

}