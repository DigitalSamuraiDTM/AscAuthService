package com.share.service.controller.ktor.plugins

import com.digitalsamurai.asc.LoggingService
import com.digitalsamurai.asc.Preferences
import com.digitalsamurai.asc.controller.entity.NetworkAppInfo
import com.digitalsamurai.asc.controller.entity.ServiceOkStatus
import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.model.AscModel
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.delay
import java.io.File


fun Application.configureRouting(model: AscModel, logger : LoggingService) {

        routing {
            val PUBLIC_PORT = KtorServer.PUBLIC_PORT
            val PRIVATE_PORT = KtorServer.PRIVATE_PORT
            var fileDescription  = ""
            var fileName = ""
            //-----------COMMON-----------

            get("/getStatus") {
                call.respondText { "Ok" }
            }
            post("/getStatus") {
                call.respond(ServiceOkStatus())
            }

            get("/getActualVersionInfo"){
                //todo return [NetworkAppInfo]
                val data = model.getActualApk()
                call.respond(NetworkAppInfo(data.version.version,data.version.update,data.version.path,data.patchNote))
            }

            get("/downloadActualVersion"){
                when(call.request.local.port){
                    PRIVATE_PORT->{
                        val info = model.getActualApk()
                        val file = info.apk
                        call.response.header("FILENAME",info.apkName)
                        call.response.header("SIZE",file.length())
                        call.response.header("VERSION",info.version.version)
                        call.response.header("UPDATE",info.version.update)
                        call.response.header("PATCH",info.version.path)
                        call.respondFile(file)
                    }
                    PUBLIC_PORT->{
                        call.respond(HttpStatusCode.NoContent)
                    }
                    else->{
                        call.respond(HttpStatusCode.BadGateway)

                    }
                }

            }
        }

    }
