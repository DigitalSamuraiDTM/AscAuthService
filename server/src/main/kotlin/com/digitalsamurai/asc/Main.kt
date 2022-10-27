package com.digitalsamurai.asc

import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.di.DaggerMainComponent
import com.digitalsamurai.asc.di.DatabaseModule
import com.digitalsamurai.asc.di.MainComponent
import com.digitalsamurai.asc.di.MainModule
import com.digitalsamurai.ascservice.di.MechModule
import org.ktorm.database.Database

fun main(args : Array<String>){

    Preferences.injectStartSettings(args)
    Preferences.prepareDI()
    KtorServer().start()
}

object Preferences{

    /**
     * Base app preferences
     * Need for work service
     * */
    private var apkStorage = ""
    private var databaseUrl = ""
    private var databaseLogin = ""
    private var jwtConfig = ""
    private var rtConfig = ""
    private var jwtToken = ""
    private var rtToken = ""
    private var databasePass = ""
    private var jsonInfoActualVersion = ""

    /**
     * Public DI component like DI component which created in Application in Android project
     * */
    lateinit var mainComponent: MainComponent



    fun injectStartSettings(args: Array<String>){
        apkStorage = args[(args.indexOf("-apk_storage")+1)]
        jwtConfig = args[(args.indexOf("-jwt_config")+1)]
        rtConfig = args[(args.indexOf("-rt_config")+1)]
        jwtToken = args[(args.indexOf("-jwt_token")+1)]
        rtToken = args[(args.indexOf("-rt_token")+1)]
        databaseUrl = args[(args.indexOf("-db_url")+1)]
        databaseLogin = args[(args.indexOf("-db_login")+1)]
        databasePass = args[(args.indexOf("-db_pass")+1)]
        jsonInfoActualVersion = args[(args.indexOf("-json_actual")+1)]
    }
    fun prepareDI(){
        val databaseModule = DatabaseModule(databaseUrl,databaseLogin, databasePass)
        val mainModule = MainModule(jsonInfoActualVersion, apkStorage)
        val mechModule = MechModule(
            jwtConfRoute = jwtConfig,
            rtConfRoute = rtConfig,
            jwtEncryptorToken = jwtToken,
            rtEncryptorToken = rtToken)
        mainComponent = DaggerMainComponent.builder()
            .mainModule(mainModule)
            .databaseModule(databaseModule).
            mechModule(mechModule).build()
    }


}