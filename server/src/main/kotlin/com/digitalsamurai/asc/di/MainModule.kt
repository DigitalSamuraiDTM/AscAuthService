package com.digitalsamurai.asc.di

import com.digitalsamurai.asc.LoggingService
import com.digitalsamurai.asc.Preferences
import com.digitalsamurai.asc.model.appupdatemanager.AscModelUpdater
import com.digitalsamurai.asc.model.appupdatemanager.AscModelUpdaterImpl
import com.digitalsamurai.asc.model.appupdatemanager.fileprovider.FileProvider
import com.digitalsamurai.asc.model.auth.AuthModel
import com.digitalsamurai.asc.model.serviceinfo.ServiceModel
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.rt.RtDao
import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.users.UserDao
import com.digitalsamurai.ascservice.mech.encryptors.AesEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.RsaEncryptor
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.rt.RtProvider
import dagger.Provides
import javax.inject.Singleton

@dagger.Module
class MainModule(private val jsonInfoActualVersion : String,
                 private val apkStorage : String) {


    @Provides
    @Singleton
    fun provideModel(fileProvider: FileProvider) : AscModelUpdater {
        return AscModelUpdaterImpl(fileProvider)
    }


    @Provides
    @Singleton
    fun provideServiceModel(jwtProvider: JwtProvider) : ServiceModel {
        return ServiceModel(jwtProvider)
    }


    @Provides
    @Singleton
    fun provideUserModel(teamDao : TeamDao,
                         userDao : UserDao) : UserModel {
        return UserModel(teamDao,userDao)
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

    @Singleton
    @Provides
    fun provideAuthModel(rsa : RsaEncryptor,
                         aes : AesEncryptor,
                         jwtProvider: JwtProvider,
                         rtProvider: RtProvider,
                         rtDao: RtDao,
                         userDao: UserDao
    ) : AuthModel {
        return AuthModel(rsa,aes, jwtProvider , rtProvider , rtDao, userDao)
    }

}