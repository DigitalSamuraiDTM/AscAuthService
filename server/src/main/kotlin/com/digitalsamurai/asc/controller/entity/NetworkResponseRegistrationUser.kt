package com.digitalsamurai.asc.controller.entity

import com.google.gson.annotations.SerializedName

data class NetworkResponseRegistrationUser (
    @SerializedName("is_ok")
    val isOk : Boolean,


    //0 - OK
    //1 - Username exist
    //2 - Telegram already linked
    //3 - other error (with message)
    @SerializedName("status")
    val status : Int,

    @SerializedName("message")
    val message : String? = null

        )