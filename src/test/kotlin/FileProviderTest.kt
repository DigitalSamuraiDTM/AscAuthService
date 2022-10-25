import com.digitalsamurai.asc.model.fileprovider.FileProvider
import org.junit.jupiter.api.Test

class FileProviderTest {

    @Test
    fun test(){
        val provider = FileProvider("D:\\bomba","D:\\bomba\\actual.json","asc2.apk")

//        println(provider.getActualVersion().toString())
//        println(provider.getActualPatchNote())

        println(provider.getActualApp().toString())

        assert(provider.getDirectoriesCountInStorage()==4)
    }
}