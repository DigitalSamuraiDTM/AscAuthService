package com.digitalsamurai.asc.model.auth

import com.digitalsamurai.asc.controller.entity.adminpanel.NetworkOkBodyResponse
import com.digitalsamurai.asc.controller.entity.auth.NetworkRequestLogin
import com.digitalsamurai.asc.controller.entity.auth.NetworkResponseKeys
import com.digitalsamurai.asc.controller.entity.auth.NetworkResponseLogin
import com.digitalsamurai.ascservice.mech.database.rt.RtDao
import com.digitalsamurai.ascservice.mech.database.rt.entity.RtTokenStatus
import com.digitalsamurai.ascservice.mech.database.users.UserDao
import com.digitalsamurai.ascservice.mech.encryptors.AesEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.RsaEncryptor
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtPayload
import com.digitalsamurai.ascservice.mech.rt.RtProvider

class AuthModel(private val rsaEncryptor: RsaEncryptor,
                private val aesEncryptor: AesEncryptor,
                private val jwtProvider: JwtProvider,
                private val rtProvider: RtProvider,
                private val rtDao: RtDao,
                private val userDao: UserDao) {

    suspend fun openApp(jwtPayload: JwtPayload?) : NetworkResponseKeys {
        if (jwtPayload!=null){
            val dbResponse = rtDao.updateLastActive(jwtPayload.user)
        }
        return NetworkResponseKeys(aesKey = aesEncryptor.getKey(), rsaKey = rsaEncryptor.getPublicKey())
    }

    suspend fun login(info: NetworkRequestLogin, userAgent : String): NetworkResponseLogin {
        val dbUser = userDao.getAllUserInfo(info.username)

        if (dbUser==null){
            return NetworkResponseLogin(false,2,null,null)
        } else{
            //todo encrypt pass before checking

            if (dbUser.tgId==null){
                return NetworkResponseLogin(false,1,null,null)

            }
            if (dbUser.password == info.password){
                val currentToken = rtDao.getActiveToken(dbUser.username)
                if (currentToken!=null){
                    rtDao.updateTokenStatus(currentToken.token,RtTokenStatus.BLOCKED)
                }
                //generate jwt/rt pair, add to db
                val jwt = jwtProvider.createNewJwtKey(dbUser,userAgent)
                val rt = rtProvider.createRtToken(jwt)
                val response = rtDao.insertRtToken(rt,dbUser.username,userAgent)
                return if (response){
                    NetworkResponseLogin(true,0,jwt,rt)
                } else{
                    NetworkResponseLogin(false,3,null,null)
                }
            } else{
                return NetworkResponseLogin(false,2,null,null)
            }
        }
    }

    suspend fun updateToken(jwt : String, rt : String, agent : String): NetworkResponseLogin {
        if (rtProvider.isJwtBelongRt(jwt,rt)){

            val payload = jwtProvider.getPayload(jwt)
            val user = userDao.getAllUserInfo(payload.user) ?: return NetworkResponseLogin(false,2,null,null)
            val newJwt = jwtProvider.createNewJwtKey(user,agent)
            var dbResponse =  rtDao.updateLastActive(payload.user)
            if (!dbResponse){
                return NetworkResponseLogin(false,3,null,null)
            }
            dbResponse = rtDao.updateTokenStatus(rt,RtTokenStatus.EXPIRED)
            if (!dbResponse){
                return NetworkResponseLogin(false,3,null,null)
            }
            val newRt = rtProvider.createRtToken(newJwt)
            dbResponse = rtDao.insertRtToken(newRt,payload.user,agent)
            return NetworkResponseLogin(true,0,newJwt,newRt)
        } else{
            return NetworkResponseLogin(false,4,null,null)
        }
    }

    suspend fun logout(jwt: String) : NetworkOkBodyResponse  {
        val rt = rtProvider.createRtToken(jwt)
        val response = rtDao.updateTokenStatus(rt,RtTokenStatus.BLOCKED)
        val errorMessage = if (response){
            null
        } else{
            "Request error"
        }
        return NetworkOkBodyResponse(response,errorMessage)
    }
}