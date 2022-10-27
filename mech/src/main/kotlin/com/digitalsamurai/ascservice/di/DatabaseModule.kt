package com.digitalsamurai.asc.di

import com.digitalsamurai.ascservice.mech.database.DatabaseImpl
import com.digitalsamurai.ascservice.mech.database.rt.RtDao
import com.digitalsamurai.ascservice.mech.database.rt.RtDaoImpl
import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.teams.TeamDaoImpl
import com.digitalsamurai.ascservice.mech.database.users.UserDao
import com.digitalsamurai.ascservice.mech.database.users.UserDaoImpl
import com.digitalsamurai.ascservice.mech.encryptors.PasswordEncryptor
import dagger.Provides
import dagger.Module
import org.ktorm.database.Database
import javax.inject.Singleton

@Module
class DatabaseModule(val databaseUrl : String, val databaseLogin : String, val databasePass : String) {



    @Provides
    @Singleton
    fun providePasswordEncryptor() : PasswordEncryptor {
        return PasswordEncryptor()
    }

    @Provides
    @Singleton
    fun provideDatabase() : Database{
        return Database.connect(databaseUrl,databaseLogin,databasePass)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: Database,
                       encryptor : PasswordEncryptor
    ) : UserDao {
        return UserDaoImpl(database,encryptor)
    }

    @Provides
    @Singleton
    fun provideTeamsDao(database: Database) : TeamDao {
        return TeamDaoImpl(database)
    }
    @Provides
    @Singleton
    fun provideRtDao(database: Database) : RtDao {
        //todo add sha256 encryptor
        return RtDaoImpl(database)
    }

    @Provides
    @Singleton
    fun provideDatabaseImpl(userDao : UserDao,
                            teamDao: TeamDao,
                            rtDao : RtDao
    ) : DatabaseImpl {
        return DatabaseImpl(userDao, teamDao,rtDao)
    }
}