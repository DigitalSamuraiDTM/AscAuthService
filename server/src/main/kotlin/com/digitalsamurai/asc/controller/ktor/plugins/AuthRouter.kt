package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.controller.ktor.KtorServer.Companion.authValid
import io.ktor.application.*
import io.ktor.routing.*
import java.net.NetworkInterface

fun Application.configureAuthRouter(){
    routing {
        val PUBLIC_PORT = KtorServer.PUBLIC_AUTH_PORT





        get("/skrmfskrf"){
            call.authValid(NetworkInterface::class){

            }
        }
    }




}