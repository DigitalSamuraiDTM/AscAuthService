package com.digitalsamurai.asc.controller.entity

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.google.gson.annotations.SerializedName

data class NetworkRegistrationUserInfo (
        @SerializedName("username")
        val username : String,

        @SerializedName("password")
        val password : String,

        @SerializedName("seleniumAccess")
        val seleniumAccess : Boolean,

        @SerializedName("carboniumAccess")
        val carboniumAccess : Boolean,

        @SerializedName("osmiumAccess")
        val osmiumAccess : Boolean,

        @SerializedName("bohriumAccess")
        val bohriumAccess : Boolean,

        @SerializedName("kryptonAccess")
        val kryptonAccess : Boolean,

        @SerializedName("Job")
        val jobLevel: JobLevel,

        @SerializedName("team")
        val team : String,

        @SerializedName("tg_id")
        val tgId : String?,

        @SerializedName("tg_tag")
        val tgTag : String?,

        @SerializedName("inviter")
        val inviter : String,

        @SerializedName("secret_key")
        override val key: String,


        ) : AuthKey