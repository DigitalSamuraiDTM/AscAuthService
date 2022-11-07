package com.digitalsamurai.asc.controller.entity.auth

import com.google.gson.annotations.SerializedName

data class NetworkResponseLogin(
    @SerializedName("is_ok")
    val isOk : Boolean,
    //0 = OK. Get JWT, RT
    //1 = OK, but no telegram
    //2 = BAD
    @SerializedName("status")
    val status : Int,

    @SerializedName("jwt")
    val jwtToken : String?,

    @SerializedName("rt")
    val rt : String?
)
