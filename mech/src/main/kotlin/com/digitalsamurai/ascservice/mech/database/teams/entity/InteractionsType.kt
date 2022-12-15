package com.digitalsamurai.ascservice.mech.database.teams.entity

import com.google.gson.annotations.SerializedName

enum class InteractionsType {
    @SerializedName("CPA")
    CPA,
    @SerializedName("RS")
    RS,
    @SerializedName("INHOUSE")
    INHOUSE,
    @SerializedName("OTHER")
    OTHER;

    companion object{
        fun fromString(string : String?) : InteractionsType?{
            return when(string){
                "CPA"->CPA
                "RS"->RS
                "INHOUSE"->INHOUSE
                "OTHER"->OTHER
                else->{null}
            }
        }
    }
}