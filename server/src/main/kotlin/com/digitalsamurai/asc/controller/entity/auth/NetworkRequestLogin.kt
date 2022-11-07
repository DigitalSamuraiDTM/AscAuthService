package com.digitalsamurai.asc.controller.entity.auth

import com.digitalsamurai.asc.controller.entity.AuthKey
import com.google.gson.annotations.SerializedName

data class NetworkRequestLogin(
    @SerializedName("user")
    val username : String,

    @SerializedName("pass")
    val password : String,


    @SerializedName("secret_key")
    override val key: String

) : AuthKey
