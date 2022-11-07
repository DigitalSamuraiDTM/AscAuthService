package com.digitalsamurai.ascservice.mech.jwt.entity

import com.google.gson.annotations.SerializedName

enum class JwtStatus {
    @SerializedName("ACTIVE")
    ACTIVE,
    @SerializedName("INVALID")
    INVALID,
    @SerializedName("EXPIRED")
    EXPIRED
}