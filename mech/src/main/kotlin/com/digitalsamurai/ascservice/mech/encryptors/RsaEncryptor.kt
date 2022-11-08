package com.digitalsamurai.ascservice.mech.encryptors

import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec
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

    private var cipherDecryptor = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    private var cipherEncryptor = Cipher.getInstance("RSA/ECB/PKCS1Padding")




    init {
        if (privateKeyString==null || publicKeyString==null){
            //generate new keys
            keyPairGenerator.initialize(2048)
            with(keyPairGenerator.genKeyPair()){
                publicRSAKey = this.public
                privateRSAKey = this.private
            }
        } else{
            //parse keys
            val keyFactory = KeyFactory.getInstance("RSA")
            publicRSAKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString!!.toByteArray(StandardCharsets.UTF_8))))
            privateRSAKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString!!.toByteArray(StandardCharsets.UTF_8))))

        }
        publicKeyString = String(Base64.getEncoder().encode(publicRSAKey.encoded))
        privateKeyString = String(Base64.getEncoder().encode(privateRSAKey.encoded))

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