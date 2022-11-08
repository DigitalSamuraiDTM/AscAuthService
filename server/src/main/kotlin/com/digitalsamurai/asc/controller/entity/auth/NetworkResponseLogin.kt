package com.digitalsamurai.asc.controller.entity.auth

import com.google.gson.annotations.SerializedName

data class NetworkResponseLogin(
    @SerializedName("is_ok")
    val isOk : Boolean,
    //0 = OK. Get JWT, RT
    //1 = OK, but no telegram
    //2 = BAD
    //3 = Server error
    //4 = Fail update jwt rt because jwt not equal rt
    @SerializedName("status")
    val status : Int,

    @SerializedName("jwt")
    val jwtToken : String?,

    @SerializedName("rt")
    val rt : String?
)
