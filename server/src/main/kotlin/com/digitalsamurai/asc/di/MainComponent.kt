package com.digitalsamurai.asc.di

import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.ascservice.di.MechModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, MechModule:: class])
interface MainComponent {

    fun injectKtorServer(server: KtorServer)

}