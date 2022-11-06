package com.digitalsamurai.asc.controller.entity

import com.google.gson.annotations.SerializedName

interface AuthKey {

    @get:SerializedName("secret_key")
    val key : String
}