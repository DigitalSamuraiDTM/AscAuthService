package com.digitalsamurai.asc.controller.entity

import com.google.gson.annotations.SerializedName

data class NetworkAppInfo (
    @SerializedName("version")
    val version : Int,
    @SerializedName("update")
    val update : Int,
    @SerializedName("patch")
    val patch : Int,

    @SerializedName("patch_note")
    val patchNote : String
        )