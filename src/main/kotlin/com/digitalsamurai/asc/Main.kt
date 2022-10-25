package com.digitalsamurai.asc

import com.digitalsamurai.asc.controller.ktor.KtorServer

fun main(args : Array<String>){

    injectStartSettings(args)
    KtorServer().start()
}
fun injectStartSettings(args: Array<String>){
    Preferences.apkStorage = args[(args.indexOf("-apk_storage")+1)]
    Preferences.databaseUrl = args[(args.indexOf("-db_url")+1)]
    Preferences.databaseLogin = args[(args.indexOf("-db_login")+1)]
    Preferences.databasePass = args[(args.indexOf("-db_pass")+1)]
    Preferences.authService = args[(args.indexOf("-auth")+1)]
    Preferences.jsonInfoActualVersion = args[(args.indexOf("-json_actual")+1)]
}
object Preferences{
    var apkStorage = ""
    var databaseUrl = ""
    var databaseLogin = ""
    var databasePass = ""
    var authService = ""
    var jsonInfoActualVersion = ""

}