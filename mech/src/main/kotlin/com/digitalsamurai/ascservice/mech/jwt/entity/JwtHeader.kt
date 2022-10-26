package com.digitalsamurai.ascservice.mech.jwt.entity

import com.google.gson.annotations.SerializedName

data class JwtHeader (

    @SerializedName("alg")
    val alg : String = "JWT",

    @SerializedName("typ")
    val type : String = "HS256"
        )