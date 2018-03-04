package com.amandalacanna.api.request

import com.amandalacanna.api.response.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsRequest {
    @GET("v2/everything?q=URL-encoded")
    fun getNews (@Query("page") page: Int,
                      @Query("pageSize") pageSize: Int): Observable<NewsResponse>

}