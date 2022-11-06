package com.digitalsamurai.ascservice.di

import com.digitalsamurai.asc.di.DatabaseModule
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.rt.RtProvider
import com.google.gson.Gson
import dagger.Provides
import dagger.Module
import java.io.File
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, EncryptorsModule::class])
class MechModule(
    private val jwtConfRoute : String,
    private val rtConfRoute : String,
    private val jwtEncryptorToken : String,
    private val rtEncryptorToken : String) {

    @Provides
    @Singleton
    fun provideJwtProvider(gson: Gson) : JwtProvider{
        return JwtProvider(jwtEncryptorToken,gson, File(jwtConfRoute))
    }
    @Provides
    @Singleton
    fun provideGson() : Gson{
        return Gson()
    }

    @Singleton
    @Provides
    fun provideRtProvider() : RtProvider{
        return RtProvider(rtEncryptorToken,File(rtConfRoute))
    }


}