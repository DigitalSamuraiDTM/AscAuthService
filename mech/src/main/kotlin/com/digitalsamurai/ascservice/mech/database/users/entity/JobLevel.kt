package com.digitalsamurai.ascservice.mech.database.users.entity

import com.google.gson.annotations.SerializedName

enum class JobLevel {
    @SerializedName("AFFILIATE")
    AFFILIATE,

    @SerializedName("WEB")
    WEB,

    @SerializedName("ARBITR_1W")
    ARBITR_1W,

    @SerializedName("TEAMLEAD")
    TEAMLEAD,

    @SerializedName("ADMIN")
    ADMIN;
    companion object{
        fun fromString(string : String?) : JobLevel?{
            return when(string){
                "AFFILIATE"->{AFFILIATE}
                "WEB"->{WEB}
                "ARBITR_1W"->{ARBITR_1W}
                "TEAMLEAD"->{TEAMLEAD}
                "ADMIN"->{ADMIN}
                else->{null}
            }
        }
    }
}