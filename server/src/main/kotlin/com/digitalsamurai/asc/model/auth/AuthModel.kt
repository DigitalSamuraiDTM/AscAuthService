package com.digitalsamurai.asc.model.auth

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
            rtDao.updateLastActive(jwtPayload.user)
        }
        return NetworkResponseKeys(aesKey = aesEncryptor.getKey(), rsaKey = rsaEncryptor.getPublicKey())
    }

    suspend fun login(info: NetworkRequestLogin, userAgent : String): NetworkResponseLogin {
        val user = userDao.getUser(info.username)
        if (user==null){
            return NetworkResponseLogin(false,2,null,null)
        } else{
            //todo encrypt pass before checking

            val currentToken = rtDao.getActiveToken(user.username)
            if (currentToken!=null){
                rtDao.updateTokenStatus(currentToken.token,RtTokenStatus.BLOCKED)
            }

            if (user.tgId==null){
                return NetworkResponseLogin(false,1,null,null)

            }
            if (user.password == info.username){
                //generate jwt/rt pair, add to db
                val jwt = jwtProvider.createNewJwtKey(user,userAgent)
                val rt = rtProvider.createRtToken(jwt)
                val response = rtDao.insertRtToken(rt,user.username,userAgent)
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

    suspend fun updateToken(jwt : String, rt : String): NetworkResponseLogin {
        return if (rtProvider.isJwtBelongRt(jwt,rt)){

            val user = jwtProvider.getPayload(jwt)
            val newJwt = jwtProvider.createNewJwtKey(user)
            var dbResponse =  rtDao.updateLastActive(user.user)
            if (!dbResponse){
                NetworkResponseLogin(false,3,null,null)
            }
            dbResponse = rtDao.updateTokenStatus(rt,RtTokenStatus.EXPIRED)
            if (!dbResponse){
                NetworkResponseLogin(false,3,null,null)
            }
            val newRt = rtProvider.createRtToken(newJwt)
            NetworkResponseLogin(true,0,newJwt,newRt)
        } else{
            NetworkResponseLogin(false,4,null,null)
        }
    }
}