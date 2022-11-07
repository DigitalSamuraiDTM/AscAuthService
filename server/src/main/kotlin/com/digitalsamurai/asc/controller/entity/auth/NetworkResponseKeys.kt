package com.digitalsamurai.asc.controller.entity.auth

import com.digitalsamurai.ascservice.mech.jwt.entity.JwtStatus
import com.google.gson.annotations.SerializedName

data class NetworkResponseKeys(
    @SerializedName("public_aes")
    val aesKey : String,
    @SerializedName("public_rsa")
    val rsaKey : String,

    @SerializedName("jwt_valid")
    var jwtValid : JwtStatus? = null
)
