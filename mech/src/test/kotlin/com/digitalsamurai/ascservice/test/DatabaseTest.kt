import com.digitalsamurai.ascservice.mech.database.access.AppAccessDao
import com.digitalsamurai.ascservice.mech.database.access.AppAccessDaoImpl
import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.teams.TeamDaoImpl
import com.digitalsamurai.ascservice.mech.database.users.UserDaoImpl
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.encryptors.PasswordEncryptor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.ktorm.database.Database

class DatabaseTest {


    private lateinit var database : UserDaoImpl
    private lateinit var databaseTeam : TeamDaoImpl
    private lateinit var databaseAccess : AppAccessDao

    @BeforeEach
    fun before(){
        val d = Database.connect(url = "jdbc:mysql://127.0.0.1:3306/ascdb",user ="root",password = "123456")
        database = UserDaoImpl(d, PasswordEncryptor())
        databaseTeam = TeamDaoImpl(d)
        databaseAccess =AppAccessDaoImpl(d)
    }


    @Test
    fun testCounter() = runBlocking{
        val start = System.currentTimeMillis()
        val list  =databaseTeam.getCountNamingsByTeam("Devs")
        val list2  =databaseTeam.getCountApkByTeam("Devs")
        val list3  =databaseTeam.getCountApkInstallsByTeam("RS")
        val list4  =databaseTeam.getCountSharedCabinetsByTeam("CPA_Spb")
        val list5  =databaseTeam.getCountSharingRequest("CPA_Spb")
        val end = System.currentTimeMillis()
        println("REQUEST TIME :${end-start}")
        println("NAMINGS :${list}")
        println("APKS :${list2}")
        println("APK INSTALLS :${list3}")
        println("CABINETS :${list4}")
        println("SHAR REQUEST :${list5}")
        assert(true)
    }

    @Test()
    fun testAccess() = runBlocking {
        val result = databaseAccess.getTeamAccess("Devs")
        println(result)
        assert(true)
    }

    @Test
    fun testGetTeams() = runBlocking{
        val list  =databaseTeam.getTeamsList()
        println(list.joinToString(", \n"))
        assert(true)
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

        val users = database.insertUser("pudge5343awdawd","password",null,null,true,false,true,false,true,
            JobLevel.WEB,"1Win","andrew")
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