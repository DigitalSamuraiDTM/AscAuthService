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
    OTHER
}