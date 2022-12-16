package com.digitalsamurai.asc.controller.entity.adminpanel

import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.google.gson.annotations.SerializedName

data class NetworkCreateTeamData (
    @SerializedName("team_name")
    val teamName : String,

    @SerializedName("note")
    val note : String?,

    @SerializedName("interaction_type")
    val interactionType : InteractionsType

        )