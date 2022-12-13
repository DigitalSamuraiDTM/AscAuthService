package com.digitalsamurai.ascservice.mech.database.access.tables

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object AppAccesses : Table<AppAccess>("app_access") {
    val teamName = varchar("team").bindTo { it.teamName }
    val appPackage = varchar("app_package").bindTo { it.appPackage }
    val accessIndex = int("access_index").bindTo { it.index }

}
interface AppAccess : Entity<AppAccess> {
    companion object : Entity.Factory<AppAccess>()

    val teamName : String
    val appPackage : String
    val index : Int

}