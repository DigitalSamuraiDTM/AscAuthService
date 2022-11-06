package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.entity.NetworkRegistrationUserInfo
import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.controller.ktor.KtorServer.Companion.authValid
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureAuthRouter(jwtProvider: JwtProvider, userModel: UserModel, gson: Gson, authEncryptor: AuthEncryptor){
    routing {

        val registrationAccessMap = mapOf<JobLevel,List<JobLevel>>(
            JobLevel.WEB to listOf(),
            JobLevel.AFFILIATE to listOf(JobLevel.WEB),
            JobLevel.TEAMLEAD to listOf(JobLevel.WEB,JobLevel.AFFILIATE, JobLevel.TEAMLEAD, JobLevel.ARBITR_1W),
            JobLevel.ARBITR_1W to listOf(JobLevel.ARBITR_1W),
            JobLevel.ADMIN to listOf(JobLevel.WEB,JobLevel.AFFILIATE,JobLevel.TEAMLEAD,JobLevel.ARBITR_1W,JobLevel.ARBITR_1W,JobLevel.ADMIN)
        )
        val PUBLIC_PORT = KtorServer.PUBLIC_AUTH_PORT




        /**
         * Registration new user
         * [ADMIN] can register ALL users type
         * [TEAMLEAD] = [ARBITR_1w],[WEB],[AFFILIATE]
         * [ARBITR_1W] = [ARBITR_1W], [WEB]
         * [AFFILIATE] = [WEB]
         * [WEB] = None
         *
         * */

        //TODO TEST
        post("/registerUser"){
            call.authValid(PUBLIC_PORT,jwtProvider,authEncryptor,gson,NetworkRegistrationUserInfo::class){ call, info ->
                val job = jwtProvider.getPayload(call.request.headers["jwt"]!!).access
                if (registrationAccessMap[job]?.contains(info?.jobLevel) == true){
                    val result = userModel.registerUser(info!!)
                    call.respond(authEncryptor.encryptAes(gson.toJson(result),info.key))
                } else{
                    call.respond(HttpStatusCode.Forbidden)
                }
            }
        }

    }






}