package com.digitalsamurai.asc.controller.entity

import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.teams.tables.Team
import com.google.gson.annotations.SerializedName

data class NetworkTeamInfo (
    @SerializedName("team_name")
    val teamName : String,

    @SerializedName("interactions_type")
    val interactionType : InteractionsType,

    @SerializedName("note")
    val note : String
    )
fun Team.toNetworkTeam() = NetworkTeamInfo(teamName,interactionsType,note)