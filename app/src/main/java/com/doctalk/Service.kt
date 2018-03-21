package com.doctalk

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karan on 21/03/18.
 */
interface Service {

    @GET("/search/users")
    fun getUsers(@Query("q",encoded = true) query:String,@Query("sort") sort:String="followers"):Observable<Response<Model>>
}