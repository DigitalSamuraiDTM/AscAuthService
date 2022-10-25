package com.share.service.controller.ktor.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.gson.*
import retrofit2.converter.gson.GsonConverterFactory
import javax.swing.text.AbstractDocument.Content

fun Application.configureSerialization(ContentNegotiation: ContentNegotiation.Feature) {
    install(ContentNegotiation){
        gson()
    }
}