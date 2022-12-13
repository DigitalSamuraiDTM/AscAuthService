package com.digitalsamurai.ascservice.mech.database.osmium_installations.tables

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.varchar

object OsmiumInstallations : Table<OsmiumInstallation>("osmium_installations"){
    val hashCode = varchar("hash_code").bindTo { it.hashCode }
    val ip = varchar("ip").bindTo { it.ip }
    val device = varchar("device").bindTo { it.device }
    val city = varchar("city").bindTo { it.city }
    val latitude = float("latitude").bindTo { it.latitude }
    val longitude = float("longitude").bindTo { it.longitude }
    val countryCode = varchar("country_code").bindTo { it.countryCode }
}
interface OsmiumInstallation : Entity<OsmiumInstallation> {
    companion object : Entity.Factory<OsmiumInstallation>()
    val hashCode : String
    val ip : String
    val device : String
    val city : String
    val latitude : Float
    val longitude : Float

    //like BR, RU, US...
    val countryCode : String
}