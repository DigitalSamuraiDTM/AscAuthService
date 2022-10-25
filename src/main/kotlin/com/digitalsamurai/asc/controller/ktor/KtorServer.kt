package com.digitalsamurai.asc.controller.ktor

import com.digitalsamurai.asc.LoggingService
import com.digitalsamurai.asc.di.DaggerMainComponent
import com.digitalsamurai.asc.di.MainModule
import com.digitalsamurai.asc.model.AscModel
import com.share.service.controller.ktor.plugins.configureMonitoring
import com.share.service.controller.ktor.plugins.configureRouting
import com.share.service.controller.ktor.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import javax.inject.Inject

class KtorServer() : Thread() {

    companion object{
        const val PRIVATE_PORT = 25640
        const val PUBLIC_PORT = 25641
    }
    private var ports = intArrayOf(PRIVATE_PORT, PUBLIC_PORT)
    @Inject
    lateinit var model: AscModel

    @Inject
    lateinit var logger : LoggingService


    init {
        with(DaggerMainComponent.builder().mainModule(MainModule()).build()){
            this.injectKtorServer(this@KtorServer)
        }
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
        configureSerialization(ContentNegotiation)
        configureMonitoring()
    }
}


