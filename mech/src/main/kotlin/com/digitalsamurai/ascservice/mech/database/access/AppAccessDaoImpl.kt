package com.digitalsamurai.ascservice.mech.database.access

import com.digitalsamurai.ascservice.mech.database.access.tables.AppAccess
import com.digitalsamurai.ascservice.mech.database.access.tables.AppAccesses
import com.digitalsamurai.ascservice.mech.database.teams.tables.Teams
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import java.lang.Exception

class AppAccessDaoImpl(private val database:Database) : AppAccessDao {

    private val Database.appAccesses get() = this.sequenceOf(AppAccesses)


    override suspend fun getTeamAccess(teamName: String): List<AppAccess> {
        return try {
            database
                .from(AppAccesses)
                .select()
                .where {
                    AppAccesses.teamName eq teamName
                }.map { database.appAccesses.entityExtractor(it) }
        } catch (e : Exception){
            emptyList()
        }
    }
}