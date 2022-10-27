package com.digitalsamurai.ascservice.mech.database.teams

import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.teams.tables.Team
import com.digitalsamurai.ascservice.mech.database.teams.tables.Teams
import com.digitalsamurai.ascservice.mech.database.users.tables.Users
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.entity.sequenceOf

internal class TeamDaoImpl(private val database : Database) : TeamDao {

    private val Database.team get() = this.sequenceOf(Teams)

    override suspend fun getTeamsList(): List<String> {
        return database.from(Teams).select(Teams.teamName).map { database.team.entityExtractor(it).teamName }
    }

    override suspend fun insertTeam(teamName: String, interactionsType: InteractionsType): Boolean {
        return database.insert(Teams){
            set(Teams.teamName,teamName)
            set(Teams.interactionsType,interactionsType)
        } ==1
    }
}