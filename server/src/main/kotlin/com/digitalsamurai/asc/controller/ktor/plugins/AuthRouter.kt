package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.entity.NetworkUpdatePassword
import com.digitalsamurai.asc.controller.entity.ServiceOkStatus
import com.digitalsamurai.asc.controller.entity.auth.NetworkRequestLogin
import com.digitalsamurai.asc.controller.entity.auth.NetworkUpdateTokenRequest
import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.controller.ktor.KtorServer.Companion.authValid
import com.digitalsamurai.asc.model.auth.AuthModel
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtPayload
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtStatus
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureAuthRouter(jwtProvider: JwtProvider,
                                    userModel: UserModel,
                                    gson: Gson,
                                    authEncryptor: AuthEncryptor,
                                    authModel: AuthModel) {
    routing {



        val registrationAccessMap = mapOf<JobLevel, List<JobLevel>>(
            JobLevel.WEB to listOf(),
            JobLevel.AFFILIATE to listOf(JobLevel.WEB),
            JobLevel.TEAMLEAD to listOf(JobLevel.WEB, JobLevel.AFFILIATE, JobLevel.TEAMLEAD, JobLevel.ARBITR_1W),
            JobLevel.ARBITR_1W to listOf(JobLevel.ARBITR_1W),
            JobLevel.ADMIN to listOf(
                JobLevel.WEB,
                JobLevel.AFFILIATE,
                JobLevel.TEAMLEAD,
                JobLevel.ARBITR_1W,
                JobLevel.ARBITR_1W,
                JobLevel.ADMIN
            )
        )
        val PUBLIC_PORT = KtorServer.PUBLIC_AUTH_PORT



        /**
         *
         * Function call when mobile app opened
         * Return public keys for encrypting secret requests
         *
         * Also check jwt valid if exist
         *
         *  */
        get("/openApp") {
            if (call.request.local.port != PUBLIC_PORT) {
                call.respond(HttpStatusCode.ServiceUnavailable)
            }else{
                val jwt = call.request.headers["jwt"]
                if (jwt != null) {
                    val payload : JwtPayload = jwtProvider.getPayload(jwt)
                    call.respond(authModel.openApp(payload).also { it.jwtValid = jwtProvider.getTokenStatus(jwt) })

                } else {
                    call.respond(authModel.openApp(null))
                }
            }
        }

        post("/updateToken"){
            call.authValid(PUBLIC_PORT,null,authEncryptor,gson, NetworkUpdateTokenRequest::class){call, info->
                val response = authModel.updateToken(info!!.jwt,info.rt)
                call.respond(authEncryptor.encryptData(gson.toJson(response)))
            }
        }

        post("/login") {
            call.authValid(PUBLIC_PORT,null,authEncryptor,gson, NetworkRequestLogin::class){ call,info->
                val header = call.request.headers["User-Agent"]
                if (header==null){
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }
                val response = authModel.login(info!!, header)

                call.respond(authEncryptor.encryptAes(gson.toJson(response),info.key))
            }
        }

        post("/logout"){
            //todo
        }


        /**
         * Registration new user
         * [ADMIN] can register ALL users type
         * [TEAMLEAD] = [ARBITR_1W],[WEB],[AFFILIATE]
         * [ARBITR_1W] = [ARBITR_1W], [WEB]
         * [AFFILIATE] = [WEB]
         * [WEB] = None
         *
         * */

        //TODO TEST
        post("/registerUser") {
            call.authValid(PUBLIC_PORT, jwtProvider, authEncryptor, gson, NetworkUpdatePassword::class) { call, info ->
                val jwt = jwtProvider.getPayload(call.request.headers["jwt"]!!)
                val response = userModel.updateUserPassword(jwt, info!!)

                call.respond(response)
            }
        }
        put("/updateUsernamePassword") {
            call.authValid(PUBLIC_PORT, jwtProvider, authEncryptor, gson, NetworkUpdatePassword::class) { call, info ->
                val jwt = jwtProvider.getPayload(call.request.headers["jwt"]!!)
                val response = userModel.updateUserPassword(jwt, info!!)
                call.respond(authEncryptor.encryptData(gson.toJson(ServiceOkStatus(response))))
            }
        }

    }
}