package `fun`.gladkikh.app.price1c

import `fun`.gladkikh.app.price1c.intity.Valuta
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        var map = mutableMapOf<Valuta,String>()

        map.getOrPut(Valuta(" 111 ".trim()),{""})
        map.getOrPut(Valuta(" 111 ")){"111"}
        map.getOrPut(Valuta(" 222 "),defaultValue = {"222"})


        Date(0L)




        assertEquals(4, 2 + 2)
    }
}
