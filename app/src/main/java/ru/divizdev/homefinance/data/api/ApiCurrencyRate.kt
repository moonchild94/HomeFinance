package ru.divizdev.homefinance.data.api

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.divizdev.homefinance.entities.CurrencyRateAPI

interface ApiCurrencyRate {

    @GET("api/v6/convert")
    fun getCurrency(@Query("q") direction:String, @Query("compact") compact:String):Call<CurrencyRateAPI>

    companion object Factory {
        fun create(): ApiCurrencyRate {
            val retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("https://free.currencyconverterapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(ApiCurrencyRate::class.java)
        }
    }
}