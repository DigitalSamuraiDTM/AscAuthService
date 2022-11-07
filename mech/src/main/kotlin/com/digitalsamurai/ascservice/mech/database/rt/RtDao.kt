package com.digitalsamurai.ascservice.mech.database.rt

import com.digitalsamurai.ascservice.mech.database.rt.entity.RtTokenStatus
import com.digitalsamurai.ascservice.mech.database.rt.tables.RtToken
import java.time.LocalDateTime

interface RtDao {
    suspend fun updateLastActive(token : String, activeDateTime : LocalDateTime) : Boolean
    suspend fun updateLastActive(user : String) : Boolean

    suspend fun insertRtToken(token: String,user : String, agent : String,createDate : LocalDateTime, status : RtTokenStatus, lastActive : LocalDateTime) : Boolean

    suspend fun insertRtToken(token: String,user : String, agent : String) : Boolean

    suspend fun updateTokenStatus(token : String, status: RtTokenStatus) : Boolean

    suspend fun getActiveToken(user : String) : RtToken?

}