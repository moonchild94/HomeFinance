package ru.divizdev.homefinance.data.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.divizdev.homefinance.data.api.ApiCurrencyRate
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.CurrencyRateAPI

class RepositoryCurrencyRate {

    private val TAG_LOG = "Currency Rate"

    private val fakeSaveCurrencyRate = mapOf(
            Currency.RUB to  mapOf(Currency.RUB to 1f, Currency.USD to 0.016f),
            Currency.USD to  mapOf(Currency.USD to 1f,  Currency.RUB to 63.49f))

    private var loadedCurrencyRate: MutableMap<Currency, Map<Currency, Float>>? = null

    fun getCurrentRate():Map<Currency, Map<Currency, Float>>{
        if(loadedCurrencyRate == null){
            return fakeSaveCurrencyRate
        }
        return loadedCurrencyRate as Map<Currency, Map<Currency, Float>>
    }


    fun createCurrencyRate(currencyRateAPI: CurrencyRateAPI ){//переделать загрузку на key-value, сейчас загрузка подходит только для одного курса
        loadedCurrencyRate = mutableMapOf()
        loadedCurrencyRate?.put(Currency.RUB, mapOf(Currency.RUB to 1f, Currency.USD to 1f/currencyRateAPI.rateUsdRub))
        loadedCurrencyRate?.put(Currency.USD, mapOf(Currency.USD to 1f, Currency.RUB to currencyRateAPI.rateUsdRub))
    }

    fun loadCurrencyRate() {


        val apiCurrencyRate = ApiCurrencyRate.create()

        val currencyRate = apiCurrencyRate.getCurrency("USD_RUB", "ultra")

        currencyRate.enqueue(object : Callback<CurrencyRateAPI> {
            override fun onResponse(call: Call<CurrencyRateAPI>, response: Response<CurrencyRateAPI>) {
                val body = response.body()
                if(body != null) {
                    createCurrencyRate(body)

                }else{
                    Log.e(TAG_LOG, "empty body")
                }

            }

            override fun onFailure(call: Call<CurrencyRateAPI>, throwable: Throwable) {
                Log.e("r", throwable.message)
                val message: String = if (throwable.message != null) {
                    throwable.message!!
                }else{
                    "error"
                }

                Log.e(TAG_LOG, message)
            }
        })

    }
}