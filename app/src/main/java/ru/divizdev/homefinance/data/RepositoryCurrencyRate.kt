package ru.divizdev.homefinance.data

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.divizdev.homefinance.data.api.IApiCurrencyRate
import ru.divizdev.homefinance.entities.CurrencyRateAPI

class RepositoryCurrencyRate {

    fun loadCurrencyRate(onLoad: (currencyRate: CurrencyRateAPI) -> Unit, onError: (errorText: String) -> Unit) {


        val apiCurrencyRate = IApiCurrencyRate.create()

        val currencyRate = apiCurrencyRate.getCurrency("USD_RUB", "ultra")

        currencyRate.enqueue(object : Callback<CurrencyRateAPI> {
            override fun onResponse(call: Call<CurrencyRateAPI>, response: Response<CurrencyRateAPI>) {
                val body = response.body()
                if(body != null) {
                    onLoad(body)
                }else{
                    onError("Empty")
                }

            }

            override fun onFailure(call: Call<CurrencyRateAPI>, throwable: Throwable) {
                Log.e("r", throwable.message)
                val message: String = if (throwable.message != null) {
                    throwable.message!!
                }else{
                    "error"
                }

                onError(message)
            }
        })

    }
}