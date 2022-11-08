package com.digitalsamurai.asc.controller.entity.auth

import com.digitalsamurai.asc.controller.entity.AuthKey
import com.google.gson.annotations.SerializedName

data class NetworkUpdateTokenRequest(
    @SerializedName("jwt")
    val jwt : String,
    @SerializedName("rt")
    val rt : String,
    @SerializedName("secret_key")
    override val key: String,

    ) : AuthKey
