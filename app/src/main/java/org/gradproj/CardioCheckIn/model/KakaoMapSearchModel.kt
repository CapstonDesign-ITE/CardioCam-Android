package org.gradproj.CardioCheckIn.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class KakaoMapSearchModel {
    @SerializedName("meta")
    @Expose
    private val metaData: org.gradproj.CardioCheckIn.model.same? = null

    @SerializedName("documents")
    @Expose
    private val documents: ArrayList<Document>? = null
}

internal class meta {
    @SerializedName("total_count")
    @Expose
    private val totalCount: Int? = null

    @SerializedName("pageable_count")
    @Expose
    private val pageableCount: Int? = null

    @SerializedName("is_end")
    @Expose
    private val isEnd: Boolean? = null

    @SerializedName("same_name")
    @Expose
    private val sameName: org.gradproj.CardioCheckIn.model.same? = null
}

internal class same {
    @SerializedName("region")
    @Expose
    private val region: List<String>? = null

    @SerializedName("keyword")
    @Expose
    private val keyword: String? = null

    @SerializedName("selected_region")
    @Expose
    private val selectedRegion: String? = null
}