package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.ktor.KtorServer
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureAuthRouter(){
    routing {
        val PUBLIC_PORT = KtorServer.PUBLIC_AUTH_PORT
    }
}