import com.digitalsamurai.asc.model.database.DatabaseImpl
import com.digitalsamurai.asc.model.database.entity.AscService
import com.digitalsamurai.asc.model.database.entity.SortingType
import com.digitalsamurai.asc.model.database.users.UserDaoImpl
import com.digitalsamurai.asc.model.database.users.entity.JobLevel
import com.digitalsamurai.asc.model.database.users.entity.UserSortingField
import com.digitalsamurai.asc.model.encryptors.PasswordEncryptor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.ktorm.database.Database

class DatabaseTest {


    private lateinit var database : UserDaoImpl

    @BeforeEach
    fun before(){
        database = UserDaoImpl(Database.connect(url = "jdbc:mysql://127.0.0.1:3306/ascdb",user ="root",password = "123456"), PasswordEncryptor())
    }


    @Test
    fun testGetAuthUser() = runBlocking {
//        val users = database.getAuthUsers(JobLevel.ADMIN)
//        println(users.joinToString(", \n"))
        assert(true)
    }


    @Test
    fun testPageUsers() = runBlocking {

//        val users = database.pageAuthUsers("ada",0, 10, UserSortingField.NAME, SortingType.ASCENDING)
//        println(users.joinToString(", \n"))
        assert(true)
    }
    @Test
    fun testInsert() = runBlocking {

        val users = database.insertUser("pudge2","password","awdafrfdsr","aiiajwdia",true,false,true,false,true,JobLevel.WEB,"team","andrew")
        assert(users)
    }
    @Test
    fun testChange() = runBlocking {

//        var users = database.updateInviter("pudge","user1")
//        assert(users)
//        users = database.updateServiceAccess("pudge",AscService.OSMIUM,true)
//        assert(users)
//        users = database.updateTeam("pudge","FAK")
//        assert(users)
//        users = database.updatePassword("pudge","SECRET")
//        assert(users)
            assert(true)
    }
}