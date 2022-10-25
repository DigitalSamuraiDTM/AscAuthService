package com.digitalsamurai.asc.di

import com.digitalsamurai.asc.controller.ktor.KtorServer
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {

    fun injectKtorServer(server: KtorServer)

}