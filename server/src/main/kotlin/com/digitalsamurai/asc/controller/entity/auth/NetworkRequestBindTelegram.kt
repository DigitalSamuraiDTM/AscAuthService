package com.digitalsamurai.asc.controller.entity.auth

import com.digitalsamurai.asc.controller.entity.AuthKey
import com.google.gson.annotations.SerializedName

data class NetworkRequestBindTelegram (
        @SerializedName("username")
        val username : String,

        @SerializedName("tg_id")
        val tgId : String,

        @SerializedName("owner")
        val owner : String?,

        @SerializedName("secret_key")
        override val key: String,

        ) : AuthKey