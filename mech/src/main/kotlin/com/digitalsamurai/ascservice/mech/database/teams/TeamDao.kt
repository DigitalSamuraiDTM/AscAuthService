package com.digitalsamurai.ascservice.mech.database.teams

import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.teams.tables.Team

interface TeamDao {
    suspend fun getTeamsList() : List<Team>

    suspend fun insertTeam(teamName : String, interactionsType: InteractionsType) : Boolean
}