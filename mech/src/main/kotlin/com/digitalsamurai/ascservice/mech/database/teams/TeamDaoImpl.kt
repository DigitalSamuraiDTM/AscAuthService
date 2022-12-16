package com.digitalsamurai.ascservice.mech.database.teams

import com.digitalsamurai.ascservice.mech.database.apks.tables.ApkRequests
import com.digitalsamurai.ascservice.mech.database.namings.tables.Namings
import com.digitalsamurai.ascservice.mech.database.osmium_installations.tables.OsmiumInstallations
import com.digitalsamurai.ascservice.mech.database.sharing.tables.SharingCabinets
import com.digitalsamurai.ascservice.mech.database.sharing.tables.SharingRequests
import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.teams.tables.Team
import com.digitalsamurai.ascservice.mech.database.teams.tables.Teams
import com.digitalsamurai.ascservice.mech.database.users.tables.Users
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import java.lang.Exception

internal class TeamDaoImpl(private val database : Database) : TeamDao {

    private val Database.team get() = this.sequenceOf(Teams)
    private val Database.namings get() = this.sequenceOf(Namings)

    override suspend fun getTeamInfo(teamName: String): Team? {
        return try {
            database.from(Teams).select().where { Teams.teamName eq teamName }.map { database.team.entityExtractor(it) }.first()
        } catch (e : Exception){
            null
        }
    }


    override suspend fun getTeamsList(): List<Team> {
        return database.from(Teams).select().map { database.team.entityExtractor(it) }
    }

    override suspend fun insertTeam(teamName: String, interactionsType: InteractionsType, note: String?): Boolean {
        return database.insert(Teams){
            set(Teams.teamName,teamName)
            set(Teams.interactionsType,interactionsType)
            set(Teams.note ,note)
        } ==1
    }

    override suspend fun getCountApkByTeam(teamName: String): Int {
        return try {
            database.from(ApkRequests).select().where {
                ApkRequests.owner inList (database.from(Users).select(Users.username).where { Users.team eq teamName })
            }.rowSet.size()
        } catch (e : Exception){
            return -1
        }
    }

    override suspend fun getCountNamingsByTeam(teamName: String): Int {
        return try {
            database.from(Namings).select().where {
                Namings.user inList (database.from(Users).select(Users.username).where { Users.team eq teamName })
            }.rowSet.size()
        } catch (e : Exception){
            return -1
        }
    }

    override suspend fun getCountApkInstallsByTeam(teamName: String): Int {
        return try {
            database.from(OsmiumInstallations)
                .select()
                .where {
                    OsmiumInstallations.hashCode inList
                            (database.from(ApkRequests)
                                .select(ApkRequests.hashRequest)
                                .where {
                        ApkRequests.owner inList
                                (database.from(Users)
                                    .select(Users.username)
                                    .where { Users.team eq teamName })
                    })
                }.rowSet.size()
        } catch (e : Exception){
            return -1
        }
    }

    override suspend fun getCountSharedCabinetsByTeam(teamName: String): Int {
        return try {
            database.from(SharingCabinets)
                .select()
                .where {
                    SharingCabinets.hashCode inList
                            (database.from(SharingRequests)
                                .select(SharingRequests.hashRequest)
                                .where {
                                    SharingRequests.owner inList
                                            (database.from(Users)
                                                .select(Users.username)
                                                .where { Users.team eq teamName })
                                })
                }.rowSet.size()
        } catch (e : Exception){
            -1
        }
    }

    override suspend fun getCountSharingRequest(teamName: String): Int {
        return try {
            database.from(SharingRequests)
                .select()
                .where { SharingRequests.owner inList
                        (database.from(Users).select(Users.username).where { Users.team eq teamName }) }.rowSet.size()
        } catch (e : Exception){
            -1
        }
    }

    override suspend fun updateInteractionsType(teamName: String, interactionsType: InteractionsType): Boolean {
        return try {
            database.update(Teams) {
                set(Teams.interactionsType, interactionsType)
                where { Teams.teamName eq teamName }
            } == 1
        } catch (e : Exception){
            false
        }
    }

    override suspend fun updateTeamName(teamName: String, newTeamName: String): Boolean {
        return try {
            database.update(Teams) {
                set(Teams.teamName, newTeamName)
                where { Teams.teamName eq teamName }
            } == 1
        } catch (e : Exception){
            false
        }
    }

    override suspend fun deleteTeam(teamName: String): Boolean {
        return try {
            database.delete(Teams){
                it.teamName eq teamName
            } == 1
        } catch (e : Exception){
            false
        }
    }

    override suspend fun updateTeamNote(teamName: String, note: String): Boolean {
        return try {
            database.update(Teams) {
                set(Teams.note, note)
                where { Teams.teamName eq teamName }
            } == 1
        } catch (e : Exception){
            false
        }
    }
}