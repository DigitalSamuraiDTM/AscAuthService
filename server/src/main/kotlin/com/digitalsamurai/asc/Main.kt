package com.digitalsamurai.asc

import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.di.MainComponent

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
    private var databasePass = ""
    private var authService = ""
    private var jsonInfoActualVersion = ""

    /**
     * Public DI component like DI component which created in Application in Android project
     * */
    lateinit var mainComponent: MainComponent



    fun injectStartSettings(args: Array<String>){
        apkStorage = args[(args.indexOf("-apk_storage")+1)]
        databaseUrl = args[(args.indexOf("-db_url")+1)]
        databaseLogin = args[(args.indexOf("-db_login")+1)]
        databasePass = args[(args.indexOf("-db_pass")+1)]
        authService = args[(args.indexOf("-auth")+1)]
        jsonInfoActualVersion = args[(args.indexOf("-json_actual")+1)]
    }
    fun prepareDI(){

    }


}