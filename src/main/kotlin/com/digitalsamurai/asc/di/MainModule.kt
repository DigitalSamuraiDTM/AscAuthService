package com.digitalsamurai.asc.di

import com.digitalsamurai.asc.LoggingService
import com.digitalsamurai.asc.Preferences
import com.digitalsamurai.asc.model.appupdatemanager.AscModelUpdater
import com.digitalsamurai.asc.model.appupdatemanager.AscModelUpdaterImpl
import com.digitalsamurai.asc.model.appupdatemanager.fileprovider.FileProvider
import dagger.Provides
import javax.inject.Singleton

@dagger.Module
class MainModule {

    val apkStorage : String
    get() = Preferences.apkStorage

    val databaseUrl : String
        get() = Preferences.databaseUrl
    val databaseLogin : String
        get() = Preferences.databaseLogin

    val databasePass : String
        get() = Preferences.databasePass

    val authService :String
        get() = Preferences.authService

    val jsonInfoActualVersion :String
    get() = Preferences.jsonInfoActualVersion



    @Provides
    @Singleton
    fun provideModel(fileProvider: FileProvider) : AscModelUpdater {
        return AscModelUpdaterImpl(fileProvider)
    }

    @Provides
    @Singleton
    fun provideFileProvider() : FileProvider {
        return FileProvider(apkStorage,jsonInfoActualVersion,"asc.apk")
    }
    @Provides
    @Singleton
    fun provideLogger() : LoggingService{
        return LoggingService()
    }
}