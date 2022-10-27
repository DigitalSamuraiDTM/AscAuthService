package com.digitalsamurai.ascservice.mech.database.rt

import com.digitalsamurai.ascservice.mech.database.rt.entity.RtTokenStatus
import com.digitalsamurai.ascservice.mech.database.rt.tables.RtToken
import com.digitalsamurai.ascservice.mech.database.rt.tables.RtTokens
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import java.time.LocalDateTime

internal class RtDaoImpl(private val database : Database) :RtDao {


    private val Database.rt get() = this.sequenceOf(RtTokens)

    override suspend fun updateLastActive(token: String, activeDateTime: LocalDateTime): Boolean {
        return try {
             database.update(RtTokens) {
                 set(RtTokens.lastActive, activeDateTime)
                 where {
                     RtTokens.token eq token
                 }
             } == 1
         } catch (e : java.lang.Exception){
             return false
         }
    }

    override suspend fun updateLastActive(token: String): Boolean {
        return updateLastActive(token, LocalDateTime.now())
    }

    override suspend fun insertRtToken(
        token: String,
        user: String,
        agent: String,
        createDate: LocalDateTime,
        status: RtTokenStatus,
        lastActive: LocalDateTime
    ): Boolean {
        return database.insert(RtTokens){
            set(RtTokens.user,user)
            set(RtTokens.userAgent, agent)
            set(RtTokens.token, token)
            set(RtTokens.creatingDate, createDate)
            set(RtTokens.status, status)
            set(RtTokens.lastActive, lastActive)
        } ==1
    }

    override suspend fun insertRtToken(token: String, user: String, agent: String): Boolean {
        val status = RtTokenStatus.ACTIVE
        val createDateTime = LocalDateTime.now()
        return insertRtToken(token, user, agent, createDateTime, status, createDateTime)
    }

    override suspend fun updateTokenStatus(token: String, status: RtTokenStatus): Boolean {
        return try {
            database.update(RtTokens) {
                set(RtTokens.status, status)
                where {
                    RtTokens.token eq token
                }
            } == 1
        } catch (e : java.lang.Exception){
            return false
        }
    }

    override suspend fun getActiveToken(user: String): RtToken? {
        return try {
            database.from(RtTokens).select().where{
                (RtTokens.user eq user) and (RtTokens.status eq RtTokenStatus.ACTIVE)
            }.map { database.rt.entityExtractor(it)}[0]
        } catch (e : java.lang.Exception){
            null
        }
    }
}