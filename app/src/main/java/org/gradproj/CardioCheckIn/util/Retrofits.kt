package org.gradproj.CardioCheckIn.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofits {
    val KakaoMaoApi = Retrofit.Builder()
        .baseUrl("https://dApi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(KakaoApiService::class.java)
}