package com.digitalsamurai.asc.controller.entity

import com.google.gson.annotations.SerializedName

data class ServiceOkStatus (
    @SerializedName("is_ok")
    val isOk : Boolean = true
)