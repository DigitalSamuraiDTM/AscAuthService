package com.digitalsamurai.apkgenerator.model.repositories.db.entity

import org.ktorm.entity.Entity


interface OsmiumInstallation : Entity<OsmiumInstallation>{
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