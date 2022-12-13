package com.digitalsamurai.ascservice.mech.database.access

import com.digitalsamurai.ascservice.mech.database.access.tables.AppAccess

interface AppAccessDao {
    suspend fun getTeamAccess(teamName : String) : List<AppAccess>
}