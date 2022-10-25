package com.digitalsamurai.asc.model.appupdatemanager.entity

import com.google.gson.annotations.SerializedName

data class AppVersion(
    @SerializedName("version")
    val version : Int,

    @SerializedName("update")
    val update : Int,

    @SerializedName("patch")
    val path : Int
) {
    override fun toString(): String {
        return "V.${version}.${update}.${path}"
    }
}
