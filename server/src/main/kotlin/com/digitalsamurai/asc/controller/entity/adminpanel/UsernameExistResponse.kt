package com.digitalsamurai.asc.controller.entity.adminpanel

import com.google.gson.annotations.SerializedName

data class UsernameExistResponse (
    @SerializedName("is_ok")
    val isOk : Boolean,

    @SerializedName("message")
    val message : String?,

    @SerializedName("is_exist")
    val isExist : Boolean?
        )