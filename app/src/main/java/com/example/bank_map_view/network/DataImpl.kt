package com.example.bank_branch_details.network

import android.content.Context
import android.util.Log
import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.network.api.Data
import com.example.bank_branch_details.network.api.RequestAuthApi
import com.example.bank_branch_details.network.api.RequestTouchPointListApi
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.model.Access_Token
import com.example.bank_branch_details.network.model.Access_TouchPointList
import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.example.bank_branch_details.ui.utils.Constant
import com.example.bank_map_view.network.api.RequestBranchDetailApi
import com.example.bank_map_view.network.api.RequestCurrencyApi
import com.example.bank_map_view.network.api.RequestServiceApi
import com.example.bank_map_view.network.api.RequestServiceDetailApi
import com.example.bank_map_view.network.model.*
import com.example.bank_map_view.network.response.BranchCodeResponse
import com.example.bank_map_view.network.response.CurrencyResponse
import com.example.bank_map_view.network.response.ServiceResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

open class DataImpl private constructor() : Data{

    private  var requestAuthApi : RequestAuthApi
    private var requestTokenApi : RequestBranchDetailApi
    private var requestTouchListApi : RequestTouchPointListApi
    private var requestCurrencyApi : RequestCurrencyApi
    private var requestServiceApi : RequestServiceApi
    private var requestServiceDetailApi : RequestServiceDetailApi

    private var context : Context? = null
    private var token : String? = null

    companion object{
        private var INSTANCE : DataImpl? = null
        fun getInstance() : DataImpl{
            if(INSTANCE == null){
                INSTANCE = DataImpl()
            }
            val instance = INSTANCE
            return instance!!
        }
    }

    init {

        fun getOkHttpClient(): OkHttpClient {
            try {
                val sslContext = SSLContext.getInstance("SSL")

                // Create activity_main trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                })

                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                builder.connectTimeout(1, TimeUnit.MINUTES)
                builder.readTimeout(1, TimeUnit.MINUTES)
                builder.writeTimeout(1, TimeUnit.MINUTES)

                return builder.build()

            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        val client = getOkHttpClient()
        val authRetrofit = Retrofit.Builder()
            .baseUrl(Constant.RequstToken_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        requestAuthApi = authRetrofit.create(RequestAuthApi::class.java)

        val detailRetrofit = Retrofit.Builder()
            .baseUrl(Constant.BranchDetail_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        requestTokenApi = detailRetrofit.create(RequestBranchDetailApi::class.java)

        val touchListRetrofit = Retrofit.Builder()
            .baseUrl(Constant.BranchDetail_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        requestTouchListApi = touchListRetrofit.create(RequestTouchPointListApi::class.java)

        val currencyRetrofit = Retrofit.Builder()
            .baseUrl(Constant.BranchDetail_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        requestCurrencyApi = currencyRetrofit.create(RequestCurrencyApi::class.java)

        val serviceRetrofit = Retrofit.Builder()
            .baseUrl(Constant.BranchDetail_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        requestServiceApi = serviceRetrofit.create(RequestServiceApi::class.java)

        val serviceDetailRetrofit = Retrofit.Builder()
            .baseUrl(Constant.BranchDetail_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        requestServiceDetailApi = serviceDetailRetrofit.create(RequestServiceDetailApi::class.java)

    }

    override fun getRequestAuth() {
      requestAuthApi.getRequestAuth("KCA","KCA","password").enqueue(object : Callback<Access_Token>{
          override fun onFailure(call: Call<Access_Token>, t: Throwable) {
              EventBus.getDefault()
                  .post(
                      RestApiEvents.ErrorInvokingAPIEvent(
                      t.localizedMessage
                  ))
          }

          override fun onResponse(call: Call<Access_Token>, response: Response<Access_Token>) {
             if(response.isSuccessful){
                 Log.i("login","if")
                 token = response.body()!!.access_token
                 getTouchPointList()
                 getBranchDetail(value = "branchCode")
                 getCurrency()
                 getService()
                 getServiceDetail(value = "service_code")

             } else {
                 Log.i("login","else")
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        "No data found"
                    ))
             }
          }
      })
    }

    override fun getTouchPointList() {
        val branch = Access_TouchPointList("All","1",16.8170872,96.1287845,"100000","5.01")
        requestTouchListApi.getTouchPointList("Bearer ${token}", branch).enqueue(object : Callback<TouchPointListResponse>{
            override fun onFailure(call: Call<TouchPointListResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(t.localizedMessage))
            }

            override fun onResponse(call: Call<TouchPointListResponse>, response: Response<TouchPointListResponse>) {
                var touchPointListResponse = response.body()
                 if(touchPointListResponse != null /*|| touchPointListResponse!!.access_ATM!!.isNotEmpty() || touchPointListResponse.access_Branch!!.isNotEmpty() || touchPointListResponse.access_Agent!!.isNotEmpty() || touchPointListResponse.access_Merchant!!.isNotEmpty()*/){

                     EventBus.getDefault()
                       .post(RestApiEvents.ShowPlaces(touchPointListResponse.access_ATM as ArrayList<Access_ATM>, touchPointListResponse.access_Branch as ArrayList<Access_Branch>, touchPointListResponse.access_Agent as ArrayList<Access_Agent>, touchPointListResponse.access_Merchant as ArrayList<Access_Merchant>))

                     EventBus.getDefault()
                         .post(RestApiEvents.ShowATMDetails((touchPointListResponse.access_ATM!!)))

                     EventBus.getDefault()
                         .post(RestApiEvents.ShowAgentDetails(touchPointListResponse.access_Agent!!))

                     EventBus.getDefault()
                         .post(RestApiEvents.ShowMerchantDetails(touchPointListResponse.access_Merchant!!))
                 }
                else{
               }
            }

        })
    }

    override fun getBranchDetail(value : String) {
        val branch = Access_BranchCode("5.01", value)
        requestTokenApi.getBranchDetail("Bearer ${token}", branch).enqueue(object : Callback<BranchCodeResponse>{
            override fun onFailure(call: Call<BranchCodeResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        t.localizedMessage
                    ))
            }

            override fun onResponse(call: Call<BranchCodeResponse>, response: Response<BranchCodeResponse>) {
                if(response.isSuccessful){
                    EventBus.getDefault()
                        .post(RestApiEvents.ShowBranchDetails(response.body()!!))
                }
                else{

                }
            }
        })
    }

    override fun getCurrency() {
        val branch = BranchCode("5.01")
        requestCurrencyApi.getCurrency("Bearer ${token}", branch).enqueue(object : Callback<CurrencyResponse> {
            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        t.localizedMessage
                    ))
            }

            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                if(response.isSuccessful){

                    EventBus.getDefault()
                        .post(RestApiEvents.ShowCurrency(response.body()!!))
                }
                else{

                }
            }

        })

    }

    override fun getService() {
        val branch = BranchCode("5.01")
        requestServiceApi.getService("Bearer ${token}", branch).enqueue(object : Callback<ServiceResponse> {
            override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        t.localizedMessage
                    ))
            }

            override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                if(response.isSuccessful){
                    EventBus.getDefault()
                        .post(RestApiEvents.ShowService(response.body()!!))
                }
                else{

                }
            }

        })
    }

    override fun getServiceDetail(value : String) {
        val branch = ServiceCode("5.01", value)
        requestServiceDetailApi.getServiceDetail("Bearer ${token}", branch).enqueue(object : Callback<ServiceResponse>{
            override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        t.localizedMessage
                    ))
            }

            override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                if(response.isSuccessful){
                    EventBus.getDefault()
                        .post(RestApiEvents.ShowServiceDetail(response.body()!!))
                }
                else{

                }
            }

        })
    }
}

