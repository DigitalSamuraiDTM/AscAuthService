package com.digitalsamurai.ascservice.mech.database.sharing.tables

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object SharingCabinets : Table<SharingCabinet>("sharing_cabinets"){
    val hashCode = varchar("hash_code").bindTo { it.hashCode }
    val accountId = varchar("advertising_account_id").bindTo { it.cabinetId }
}
interface SharingCabinet : Entity<SharingCabinet> {
    companion object : Entity.Factory<SharingCabinet>()

    val hashCode:String
    val cabinetId: String
}