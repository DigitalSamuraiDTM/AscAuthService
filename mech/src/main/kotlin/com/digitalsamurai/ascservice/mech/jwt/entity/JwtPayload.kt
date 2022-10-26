package com.digitalsamurai.ascservice.mech.jwt.entity

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.google.gson.annotations.SerializedName

data class JwtPayload(

    @SerializedName("user")
    val user : String,

    @SerializedName("date_death")
    val dateDeath : String,

    @SerializedName("device")
    val device : String,

    @SerializedName("FACS")
    val FACS : Boolean,

    @SerializedName("TS")
    val TS : Boolean,

    @SerializedName("MAAS")
    val MAAS : Boolean,

    @SerializedName("DNS")
    val DNS : Boolean,

    @SerializedName("PAGS")
    val PAGS : Boolean,

    @SerializedName("access")
    val access : JobLevel,

    @SerializedName("team")
    val team :String

)
