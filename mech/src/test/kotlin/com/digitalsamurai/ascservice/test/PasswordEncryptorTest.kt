import com.digitalsamurai.ascservice.mech.encryptors.PasswordEncryptor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class PasswordEncryptorTest {

    lateinit var encryptor: PasswordEncryptor
    @BeforeEach
    fun before(){
        encryptor = PasswordEncryptor()
    }

    @Test
    fun encryptStringTest(){
        val text = "password"
        val outputText = encryptor.encryptStringData(text)
        println(outputText)

        assert(true)
    }
}