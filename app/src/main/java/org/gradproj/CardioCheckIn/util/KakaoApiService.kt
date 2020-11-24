package org.gradproj.CardioCheckIn.util

import org.gradproj.CardioCheckIn.model.KakaoMapSearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApiService {
    @Headers("Authorization: KakaoAK 44b3f6aa35e897afcd760f6400a1892c")
    @GET("/v2/local/search/keyword.json")
    fun categoryList(
        @Query("page") page: Int,
        @Query("query") searchData: String?
    ): Call<KakaoMapSearchModel?>?
}