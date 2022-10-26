package com.digitalsamurai.ascservice.mech.database.teams

interface TeamDao {
    suspend fun getTeamsList() : List<String>
}