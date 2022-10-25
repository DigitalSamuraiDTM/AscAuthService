package com.digitalsamurai.asc.model.database.teams

interface TeamDao {
    suspend fun getTeamsList() : List<String>
}