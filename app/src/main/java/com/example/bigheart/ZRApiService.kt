package com.example.bigheart

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ZRApiService {

    @GET("google/qualifications.json")
    fun nameCheck(): Observable<QualificationsModel.Result>

    @GET("google/qualifications.json")
    fun childCheck(): Observable<QualificationsModel.Result>

    companion object {
        fun create(): ZRApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://www.zackeryrobinson.com/")
                    .build()

            return retrofit.create(ZRApiService::class.java)
        }
    }
}
