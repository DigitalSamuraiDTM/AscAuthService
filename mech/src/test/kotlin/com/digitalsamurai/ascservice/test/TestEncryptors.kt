package com.digitalsamurai.ascservice.test

import com.digitalsamurai.ascservice.mech.encryptors.AesEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.RsaEncryptor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Base64
import javax.crypto.spec.SecretKeySpec

class TestEncryptors {

    private lateinit var authEncryptor: AuthEncryptor
    private lateinit var aes: AesEncryptor
    private lateinit var rsa: RsaEncryptor

    @BeforeEach
    fun before(){
//        aes = AesEncryptor(null)
//        rsa = RsaEncryptor(null,null)
//        authEncryptor = AuthEncryptor(rsaEncryptor = rsa, aesEncryptor = aes)
    }
    @Test
    fun testAes(){

        val data = "FACK OBEMA"
        var resultEncr = aes.encryptData(data.toByteArray())
        println("encrpyted: ${Base64.getEncoder().encodeToString(resultEncr)}")
        var resultDecr = aes.decryptData(resultEncr)
        println("decrypted: ${Base64.getEncoder().encodeToString(resultDecr)}")
        assert(data==String(resultDecr))
    }

    @Test
    fun testRsaWithAes(){


        println("RSA PUBLIC: ${rsa.getPublicKey()}")
        println("RSA PRIVATE: ${rsa.getPrivateKey()}")
        println("AES: ${aes.getKey()}")
        val data = "{\n" +
                "     \"user\":\"andrew\",\n" +
                "     \"pass\":\"andrew\",\n" +
                "     \"secret_key\":\"aGdkMDRzbXhibzd1OWxkOA==\"\n" +
                " }"
        val encrAes = aes.encryptData(data.toByteArray())
        println("encrypted aes: ${Base64.getEncoder().encodeToString(encrAes)}")
        val encrRsa = rsa.encryptData(encrAes)
        println("encrypted rsa: ${Base64.getEncoder().encodeToString(encrRsa)}")
        val decrRsa = rsa.decryptData(encrRsa)
        println("decrypted rsa: ${Base64.getEncoder().encodeToString(decrRsa)}")
        val decrAes = aes.decryptData(decrRsa)
        println("decrypted aes: ${Base64.getEncoder().encodeToString(decrAes)}")
        println(String(decrAes))
        assert(data == String(decrAes))


    }

    @Test
    fun testAuth(){
        val aes = AesEncryptor("ZWR2bHY4N2hiM3hhYWk1Yw==")
        val a = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwkThAKseePutJ6DZtZDTiQvPUzpjdKQrpHbFP4Vuj0RpVEZbGv8jtFGYS+vXsTIVH7c5nETy6K12udNH9aoaSWY5pxpJcwV25KJ4oJTGGXFd2JRsGpEqYbVKH9DohXngmnLahj8xyXUMnjlbfJSmFKsEA7NIEf3bWQGYht+qV7CzTepbD1ujkDMTHk/ES5r5V7M6N8a1ZfaphvZwmB/7rGgzaLPEwvHxIGNXFIPj2ofBbh8KCs4VeSLW/BS59eKzwR0MRFD24RAVM8YW7CffaQOibpUhuGTgHkSHN6qtrc26adVf+fh5MrdyLYxjz/zP45seTFlLOyRnnPB9Pc7baQIDAQAB"
        val rsa = RsaEncryptor(a,
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDCROEAqx54+60noNm1kNOJC89TOmN0pCukdsU/hW6PRGlURlsa/yO0UZhL69exMhUftzmcRPLorXa500f1qhpJZjmnGklzBXbkoniglMYZcV3YlGwakSphtUof0OiFeeCactqGPzHJdQyeOVt8lKYUqwQDs0gR/dtZAZiG36pXsLNN6lsPW6OQMxMeT8RLmvlXszo3xrVl9qmG9nCYH/usaDNos8TC8fEgY1cUg+Pah8FuHwoKzhV5Itb8FLn14rPBHQxEUPbhEBUzxhbsJ99pA6JulSG4ZOAeRIc3qq2tzbpp1V/5+Hkyt3ItjGPP/M/jmx5MWUs7JGec8H09zttpAgMBAAECggEAFwgW+98NsQ63d+IGRZQavB3yEb4zodW/1dL97hq54ZLdAPCJZxE2EK/2xBN9IiUbo4OzuQ7klIjSMVQ27Q1iae7MaX8Ak7ojXxZTAki5FrWdyGpKSbkJowVc+Ropov9wzSbOOQm/c5CksxJ+Qe4WpY8j3fNwmS91RJ9fj6fvOE+GBDSPfbXjIYo9xiRtcpdzXBFmcR0SbpHLT/99be3NVE1r12jnh29TE7xprWVwP96C07esGiQlzOLKypajfaY2PTfBgnMDF6F5Oe7AOwb7ZNqjnI0yMMEEBLf+BwiMKqt2ioBofAiXCv2NahMTWtFoi7wixXgZ9FALKfXPdEUpoQKBgQDiNYbx2vwXOD1hfR2XJGsEpx8l9Sw0CU+zfR4GF6hPeUwrCRhmivOEWip1g+PPEKGJjiqA0MfNgecXvyR3ESKqay6whhezdCq6F6AaJf2Jr54t8tnyPClIgD0ZZ6UWJA5Be6FoSR1lvwbEDYz1Fm0RgDSo9iv73Q0LF9fPA6BG2QKBgQDb2oRivvuDoZQj3YBPb2WY5Sv8pvrk4KkKbH/DRoMUqsDC3cpvyy/a7Tu5y15JckXgjefflQDFvqh6kcp5qR2DsyAgpL7meTmCs07Dhobn2vP7cyE6A4+kgkLxIql0JPrDngATDmsQ1HKqmGhb0wN8wCTIsT31rUhU4k7Xl1j/EQKBgHuJfgPxcwD/Ts5B9PoJAoUM+/7P0W8tsqA0cUQkgV+9v7I1CmVd9zXNxChfefcofv/PazjkTGnYSqt2UOTRr9vgIyR2ZtUzWO4+XspI+xjJlxpC6XHYFzeoCTYpm0fnMv3iFbkNTQe29mv1doiW0cPf/2JS5pxrEWeAgvRkZ5CpAoGAPtsluFmj7QlYIjzkv1E4+dKjldNjreYxTiKhEIJrXZBIFi50/ytXXj8vMTpclaEPK1lG7txZ4S9WWdyux8O7BXlD+TFAxwHnmF+NaEdg8I7f7IcI+94ixkTbwLdJPUSJUT4uBUS9BY3HztBOHdqhh5wjqCFMdp35LROauPazYhECgYAV5icTAt6BredFdqLoCn6b0TkGbAQoD1pwFHT62H0phcl9qkCPREvPLa5qTvymYZFLVPOQknI9ocDqZX78vpUh2Pdqpz9YdOz8h6UgQWgoXkxGmIvVpjNR7mmT3/WtlMpuX5dW81OgN8PyM/G5jctCpytf7Vo68tNWAXU96ETZlQ==")

//        val sample = "ura3V7CbWRozGR7oXRlpeQxCjtyv67I3kY+4dN9whiJeNqfvbrOYUVfjGuTC/K9NHlBP4h41nV4FVsKEIDWs7N8ke4GN5fboD0yGiOz3RrKvq2CqsW1NpO2H1IyzOiP1bdoYsN89tndhjXByTJTs+n+XGQQbjOr9z0xVDqUOYPDEC9cVSXDlwF7E02f5rLOXR1zu4BXGllT9cfxTFs5Iyqzm30CH+fnGrBokqP3YlFZ7W1pzPB0XC47LQlZAuCZJd98JYyB8zBgfEmFC0DkwaVrH/UpOEh+R4iYFLWpfWzwqAIP/7UJlvVwgg5BZN9BGWCoTWxmaLlX+RwvTZOmtKA=="
        val authEncryptor = AuthEncryptor(rsaEncryptor = rsa, aesEncryptor = aes)
//        println(authEncryptor.getPublicRSAKey())
        assert(a == authEncryptor.getPublicRSAKey())
        val data ="{\n" +
                "    \"username\":\"andrew\",\n" +
                "    \"new_password\":\"testBomba\",\n" +
                "    \"secret_key\":\"q0hlfhuhs6bc2j9v\"\n" +
                "}"
        val encryptedData = authEncryptor.encryptData(data)
        println(encryptedData)
        val decryptedData  =authEncryptor.decryptData(encryptedData)
        println(decryptedData)
        assert(decryptedData==data)
    }
    @Test
    fun generateRandomAes(){
        val aes2 = AesEncryptor(null)
        println(aes2.getKey())
        assert(true)
    }

    @Test
    fun decryptAes(){
        aes = AesEncryptor(null)
        val data ="BDIkm4m87CroT1AXv9cYTwPVz_4GuuIR2qGtdnd6iRVYPeDXQpomhAMc6ANZEe7ARsHz471EUD4xmFmoMQK2BbBNKW5eJ_MGSRFI-6lavLv9b_UQY0MWdrHGi4h3I5nbdHv13Uu_zE9s3TaZghODGISonbxZ-BNyFNnIV1qlD3e_MIbiwQ29AIV5wGHpa1HtSKBwVCXg_I7beQEWA06-GVVh6wVrH6fnLNiNJHv1W-nm-cXtIWjWkyD2Xkt-jCsDtzoL2BQKfUCmJ1QOCHEvDwyi-b_W-iR9Vc5PgCcItkj9uSnw6AX0shXmJ3_rNKyqTF0Y89ld7xbWX2T3FRKnTRddcIfjBccTfaoE5KLE5DuiYgX0_RqiEjadcswuIBAjMoBSxJ3hCTjXV8sPyhZlLEOXaNtI9PV3BexqGpDVP8d-yNM_eMAU_Ww5W3ejp0J-_ylF_3BVSivCKQpL5vhmeNO6MWVPQvbuXzMJtwOTmdD9Er-8e_doKON9xI9SA1IMeDeYfs0jwn3NO3jvQcuUVbBpnqkw8Z9Q7WIA6p-_M6o="
        println(String(aes.decryptData(Base64.getUrlDecoder().decode(data),SecretKeySpec("q0hlfhuhs6bc2j9v".toByteArray(),"AES"))))
        assert(true)

    }
}