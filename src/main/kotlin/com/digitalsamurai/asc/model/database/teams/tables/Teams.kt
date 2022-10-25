package com.digitalsamurai.asc.model.database.teams.tables

import com.digitalsamurai.asc.model.database.teams.entity.InteractionsType
import com.digitalsamurai.asc.model.database.teams.tables.Teams.bindTo
import com.digitalsamurai.asc.model.database.users.tables.User
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.varchar

object Teams : Table<Team>("teams"){
    val teamName = varchar("team_name").bindTo { it.teamName }
    val interactionsType = enum<InteractionsType>("interactions_type")

}
interface Team : Entity<Team>{
    companion object : Entity.Factory<Team>()

    val teamName : String
    val interactionsType : InteractionsType
}