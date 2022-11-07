package com.digitalsamurai.ascservice.di

import com.digitalsamurai.ascservice.mech.encryptors.AesEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.RsaEncryptor
import dagger.Provides
import javax.inject.Singleton

@dagger.Module()
class EncryptorsModule(private val aesKey : String?,
                       private val rsaPublicKey : String,
                       private val rsaPrivateKey : String?) {



    @Singleton
    @Provides
    fun provideAesEncryptor() : AesEncryptor{
        return AesEncryptor(aesKey)
    }

    @Singleton
    @Provides
    fun provideRsaEncryptor() : RsaEncryptor{
        return RsaEncryptor(rsaPublicKey,rsaPrivateKey)
    }

    @Singleton
    @Provides
    fun provideAuthEncryptor(rsa : RsaEncryptor,
                             aes : AesEncryptor) : AuthEncryptor{
        return AuthEncryptor(rsa,aes)
    }


}