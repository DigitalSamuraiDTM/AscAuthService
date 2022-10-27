package com.digitalsamurai.ascservice.mech.database.teams

import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType

interface TeamDao {
    suspend fun getTeamsList() : List<String>

    suspend fun insertTeam(teamName : String, interactionsType: InteractionsType) : Boolean
}