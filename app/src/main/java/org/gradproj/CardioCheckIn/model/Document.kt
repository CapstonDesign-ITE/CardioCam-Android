package org.gradproj.CardioCheckIn.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Document {
    @SerializedName("place_name")
    @Expose
    private val placeName: String? = null

    @SerializedName("distance")
    @Expose
    private val distance: String? = null

    @SerializedName("place_url")
    @Expose
    private val placeUrl: String? = null

    @SerializedName("category_name")
    @Expose
    private val categoryName: String? = null

    @SerializedName("address_name")
    @Expose
    private val addressName: String? = null

    @SerializedName("road_address_name")
    @Expose
    private val roadAddressName: String? = null

    @SerializedName("id")
    @Expose
    private val id: String? = null

    @SerializedName("phone")
    @Expose
    private val phone: String? = null

    @SerializedName("category_group_code")
    @Expose
    private val categoryGroupCode: String? = null

    @SerializedName("category_group_name")
    @Expose
    private val categoryGroupName: String? = null

    @SerializedName("x")
    @Expose
    private val x: String? = null

    @SerializedName("y")
    @Expose
    private val y: String? = null
}