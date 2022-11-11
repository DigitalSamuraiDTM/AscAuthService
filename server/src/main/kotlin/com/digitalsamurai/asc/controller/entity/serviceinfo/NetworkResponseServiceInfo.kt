package com.digitalsamurai.asc.controller.entity.serviceinfo

import com.google.gson.annotations.SerializedName

data class NetworkResponseServiceInfo(
    @SerializedName("jwt_validation_key")
    val jwtKey : String
)
