package com.digitalsamurai.asc.controller.ktor

import com.digitalsamurai.asc.LoggingService
import com.digitalsamurai.asc.Preferences
import com.digitalsamurai.asc.controller.ktor.plugins.configureAdminPanelRouting
import com.digitalsamurai.asc.model.appupdatemanager.AscModelUpdater
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.encryptors.AesEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtStatus
import com.google.gson.Gson
import com.share.service.controller.ktor.plugins.configureMonitoring
import com.share.service.controller.ktor.plugins.configureRouting
import com.share.service.controller.ktor.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import javax.inject.Inject
import kotlin.reflect.KClass

class KtorServer() : Thread() {

    companion object{
        const val PRIVATE_ASC_PORT = 25640
        const val PUBLIC_ASC_PORT = 25641
        const val PUBLIC_AUTH_PORT = 25650


        suspend inline fun <T : Any> ApplicationCall.authValid(port: Int,jwtProvider: JwtProvider?,authEncryptor: AuthEncryptor,gson: Gson,body : KClass<T>?,
        workFunction: (ApplicationCall, requestBody : T?) -> Unit){
            if (this.request.local.port!=port){
                this.respond(HttpStatusCode.ServiceUnavailable)
                return
            }
            if (jwtProvider!=null){
                val jwt = this.request.headers["jwt"]
                if (jwt==null){
                    this.respond(HttpStatusCode.Unauthorized)
                    return
                } else{
                    val status = jwtProvider.getTokenStatus(jwt)
                    if (status!=JwtStatus.ACTIVE){
                        this.respond(HttpStatusCode.Unauthorized)
                        return
                    }
                }
            }

            if (body!=null){
                var data = this.request.queryParameters["data"].toString()
                try {
                    data = authEncryptor.decryptData(data)
                    val obj = gson.fromJson(data,body.javaObjectType)
                    workFunction(this,obj)
                    return
                } catch (e : java.lang.Exception){
                    this.respond(HttpStatusCode.BadRequest)
                    return
                }


            }  else{
                workFunction(this,null)
            }
        }

        suspend inline fun ApplicationCall.checkJwtValid(
            vararg access: JobLevel, port: Int, jwtProvider: JwtProvider,
            workFunction: (ApplicationCall) -> Unit
        ) {
            if (this.request.port() != port) {
                this.respond(HttpStatusCode.ServiceUnavailable)
                return
            }
            val jwtString = this.request.headers.get("jwt")
            if (jwtString == null) {
                this.respond(HttpStatusCode.Unauthorized)
                return
            }
            if (access.isNotEmpty()) {
                if (!jwtProvider.checkJwtJobAccess(jwtString, access.toList())) {
                    this.respond(HttpStatusCode.Forbidden)
                    return
                }
            }
            when (jwtProvider.getTokenStatus(jwtString)) {
                JwtStatus.EXPIRED, JwtStatus.INVALID -> {
                    this.respond(HttpStatusCode.Unauthorized)
                }
                JwtStatus.ACTIVE -> {
                    workFunction.invoke(this)
                    return
                }
            }
        }
    }
    private var ports = intArrayOf(PRIVATE_ASC_PORT, PUBLIC_ASC_PORT)
    @Inject
    lateinit var model: AscModelUpdater

    @Inject
    lateinit var logger : LoggingService

    @Inject
    lateinit var jwtProvider : JwtProvider

    @Inject
    lateinit var userModel : UserModel

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var authEncryptor: AuthEncryptor



    init {
        Preferences.mainComponent.injectKtorServer(this)

    }

    fun runServer(args: Array<String>) {
        start()
//        LoggingService.info("Ktor start OK")
    }
    override fun run() {
        val env = applicationEngineEnvironment {
            module {
                module()
            }
            repeat(2){
                connector {
                    host = "0.0.0.0"
                    port = ports[it]
                }
            }
        }
        embeddedServer(Netty,env).start(true)
        super.run()
    }

    fun Application.module(){

        configureRouting(model, logger)
        configureAdminPanelRouting(jwtProvider, userModel)
        configureSerialization(ContentNegotiation)
        configureMonitoring()
    }

}


