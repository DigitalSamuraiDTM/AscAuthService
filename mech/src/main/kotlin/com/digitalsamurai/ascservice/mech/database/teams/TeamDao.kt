package com.digitalsamurai.ascservice.mech.database.teams

import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.teams.tables.Team

interface TeamDao {

    suspend fun getTeamsList() : List<Team>

    suspend fun insertTeam(teamName : String, interactionsType: InteractionsType, note : String?) : Boolean

    suspend fun getCountApkByTeam(teamName : String) : Int

    suspend fun getCountNamingsByTeam(teamName :String) : Int

    suspend fun getCountApkInstallsByTeam(teamName : String) : Int

    suspend fun getCountSharedCabinetsByTeam(teamName : String) : Int

    suspend fun getTeamInfo(teamName : String) : Team?

    suspend fun getCountSharingRequest(teamName : String ): Int

    suspend fun updateInteractionsType(teamName: String, interactionsType: InteractionsType): Boolean

    suspend fun updateTeamName(teamName: String, newTeamName : String): Boolean

    suspend fun updateTeamNote(teamName: String, note : String): Boolean
    suspend fun deleteTeam(teamName: String): Boolean
}