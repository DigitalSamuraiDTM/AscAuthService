package com.digitalsamurai.ascservice.mech.encryptors

import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class RsaEncryptor(private var publicKeyString : String?,
                    private var privateKeyString : String?) {

    private val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    private var publicRSAKey : PublicKey
    private var privateRSAKey : PrivateKey

    private var cipherDecryptor = Cipher.getInstance("RSA")
    private var cipherEncryptor = Cipher.getInstance("RSA")




    init {
        if (privateKeyString==null || publicKeyString==null){
            //generate new keys
            keyPairGenerator.initialize(2048)
            with(keyPairGenerator.genKeyPair()){
                publicRSAKey = this.public
                privateRSAKey = this.private
                publicKeyString = Base64.getEncoder().encodeToString(publicRSAKey.encoded)
                privateKeyString = Base64.getEncoder().encodeToString(privateRSAKey.encoded)
            }
        } else{
            //parse keys
            val keyFactory = KeyFactory.getInstance("RSA")
            publicRSAKey = keyFactory.generatePublic(SecretKeySpec(publicKeyString!!.toByteArray(StandardCharsets.UTF_8),"RSA"))
            privateRSAKey = keyFactory.generatePrivate(SecretKeySpec(privateKeyString!!.toByteArray(StandardCharsets.UTF_8),"RSA"))
        }


        cipherDecryptor.init(Cipher.DECRYPT_MODE,privateRSAKey)
        cipherEncryptor.init(Cipher.ENCRYPT_MODE,publicRSAKey)
    }


    fun encryptData(data : ByteArray) : ByteArray{
        var result = cipherEncryptor.doFinal(data)
        return result
    }
    fun decryptData(data : ByteArray) : ByteArray{
        var result = cipherDecryptor.doFinal(data)
        return result
    }
    fun getPublicKey() : String{
        return publicKeyString!!
    }
    fun getPrivateKey() : String{
        return privateKeyString!!
    }
}