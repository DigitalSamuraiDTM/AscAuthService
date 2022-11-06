package com.digitalsamurai.asc.controller.entity

import com.google.gson.annotations.SerializedName

data class NetworkResponseRegistrationUser (
    @SerializedName("is_ok")
    val isOk : Boolean
        )