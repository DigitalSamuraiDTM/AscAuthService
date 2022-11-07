package com.digitalsamurai.asc.controller.entity

import com.google.gson.annotations.SerializedName

data class NetworkUpdatePassword (

    @SerializedName("username")
    val username : String,

    @SerializedName("new_password")
    val newPassword : String,

        )