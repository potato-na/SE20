import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertSame

class CheckerTest {
    var c_ = Checker()

    /**
     * @author mori 2020/11/30
     * Check the Carmichael numbers under 100000 are not a prime number.
     */
    fun CheckerTest01(){
        val cnum = listOf(561L, 1105L, 1729L, 2465L, 2821L, 6601L, 8911L, 10585L, 15841L, 29341L, 41041L, 46657L, 52633L, 62745L, 63973L, 75361L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test001 is OK!")
    }


    /**
     *  @author thomas 2020/12/01
     * Check the super-Poulet numbers under 90000 are not a prime number.
     */
    fun CheckerTest02(){
        val cnum = listOf(341L, 1387L, 2047L, 2701L, 3277L, 4033L, 4369L, 4681L, 5461L, 7957L, 8321L, 10261L, 13747L, 14491L, 15709L, 18721L, 19951L, 23377L, 31417L, 31609L, 31621L, 35333L, 42799L, 49141L, 49981L, 60701L, 60787L, 65077L, 65281L, 80581L, 83333L, 85489L, 88357L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test002 is OK!")
    }

    /**
     * @author Johan 2020/12/01
     * Check if the Euler–Jacobi pseudoprimes under than 100000 for bases 10 are not a prime number.
     */
    fun CheckerTest03(){
        val cnum = listOf(9L, 91L, 481L, 1729L, 4187L, 6533L, 6601L, 8149L, 8401L, 10001L, 11111L, 11169L, 11649L, 12801L, 15841L, 19201L, 20961L, 21931L, 24013L, 34441L, 41041L, 50851L, 50881L, 63973L, 69921L, 75361L, 79003L, 83119L, 94139L, 95161L, 97681L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test003 is OK!")
    }

    /**
     * @author Nanai Kouki 2020/12/02
     * 強いリュカ擬素数20個（Strong Lucas pseudoprimes）.
     */
    fun CheckerTest04(){
        val cnum = listOf(5459L, 5777L, 10877L, 16109L, 18971L, 22499L, 24569L, 25199L, 40309L, 58519L, 75077L, 97439L, 100127L, 113573L, 115639L, 130139L, 155819L, 158399L, 161027L, 162133L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test004 is OK!")
    }

    /**
     * @author kojima 2020/12/02
     *カタラン素数3つ (Catalan pseudoprimes)
     */
    fun CheckerTest05(){
        val cnum = listOf(5907L, 1194649L, 12327121L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test008 is OK!")
    }

    /**
     * @author Taiki Hanada 2020/12/03
     * Check the Frobenius pseudoprimes with respect to Fibonacci polynomial x^2 - x - 1 are not a prime number.
     */
    fun CheckerTest06(){
        val cnum = listOf(4181L, 5777L, 6721L, 10877L, 13201L, 15251L, 34561L, 51841L, 64079L, 64681L, 67861L, 68251L, 75077L, 90061L, 96049L, 97921L, 100127L, 113573L, 118441L, 146611L, 161027L, 162133L, 163081L, 186961L, 197209L, 219781L, 231703L, 252601L, 254321L, 257761L, 268801L, 272611L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test006 is OK!")
    }

    /**
     * @author Riku Iikura 2020/12/03
     * Check the Fermat pseudoprimes to base 2, also called Sarrus numbers or Poulet numbers.
     */
    fun CheckerTest07(){
        val cnum = listOf(341L, 561L, 645L, 1105L, 1387L, 1729L, 1905L, 2047L, 2465L, 2701L, 2821L, 3277L, 4033L, 4369L, 4371L, 4681L, 5461L, 6601L, 7957L, 8321L, 8481L, 8911L, 10261L, 10585L, 11305L, 12801L, 13741L, 13747L, 13981L, 14491L, 15709L, 15841L, 16705L, 18705L, 18721L, 19951L, 23001L, 23377L, 25761L, 29341L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test007 is OK!")
    }

    /**
     * @author Takumi Nakai 2020/12/03
     * Check Grothendieck prime is not a prime number.
     */
    fun CheckerTest08(){
        val cnum = listOf(57L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test008 is OK!")
    }

    /**
     * @author Amiya Tomoaki 2020/12/03
     * Check the Euler elliptic Carmichael numbers for the elliptic curve y^2 = x^3 + 80 under 100000 are not a prime number.
     */
    fun CheckerTest09(){
        val cnum = listOf(481L, 1679L, 1763L, 3599L, 4991L, 5183L, 6119L, 7859L, 9271L, 9407L, 9599L, 18239L, 24119L, 24511L, 24803L, 31919L, 38111L, 38999L, 46079L, 56159L, 57599L, 58463L, 62863L, 63503L, 67199L, 72899L, 82679L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test009 is OK!")
    }

    /**
     * @author Takumi Maeda 2020/12/03
     * Check Perrin pseudoprimes less than 1000000 are not a prime number.
     */
    fun CheckerTest10(){
        val cnum = listOf(271441L, 904631L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test010 is OK!")
    }

    /**
     * @author Kosei Nagasawa 2020/12/04
     * Check Cipolla pseudoprimes from 1st to 8th
     */
    fun CheckerTest11(){
      val cnum = listOf(341L, 5461L, 1398101L, 22369621L, 5726623061L, 91625968981L, 23456248059221L, 96076792050570581L)
      for (num in cnum){
          assertFalse { c_.isPrime(num) }
          assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
      }
      println("Test011 is OK!")
    }
    
      /**
     *  @author Edwin Brossette 2020/12/05
     * Check the 18th first super-Poulet numbers with more than two different prime factors .
     */
    fun CheckerTest12(){
        val cnum = listOf(294409L, 1398101L, 1549411L, 1840357L, 12599233L, 13421773L, 15162941L, 15732721L, 28717483L, 29593159L, 61377109L, 66384121L, 67763803L, 74658629L, 78526729L, 90341197L, 96916279L, 109322501L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test012 is OK!")
    }

      /**
     *  @author Wataru 2020/12/07
     * Check perfect number less than 10000 are not a prime number.
     */
    fun CheckerTest13(){
        val cnum = listOf(6L, 28L, 496L, 8128L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test013 is OK!")
    }

    /**
     * @author Naoki Motoyama 2020/12/07
     * Check the Narcissistic Numbers from 3 to 10 digits. 
     */
    fun CheckerTest14() {
        val cnum = listOf(153L, 370L, 371L, 407L, 1634L, 8208L, 9478L, 54748L, 92727L, 93084L, 548834L, 1741725L, 4210818L, 9800817L, 9926315L, 24678050L, 24678051L, 88593477L, 146511208L, 472335975L, 534494836L, 912985153L, 4679307774L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test014 is OK!")
    }

    fun binPowTest01(){
        assertEquals(c_.binPow(2.toBigInteger(),9.toBigInteger(),1000.toBigInteger()),512.toBigInteger())
        assertEquals(c_.binPow(6.toBigInteger(),5.toBigInteger(),31.toBigInteger()),26.toBigInteger())
        assertEquals(c_.binPow(111111111.toBigInteger(),11111111111.toBigInteger(),1000000007.toBigInteger()),249517433.toBigInteger())
        println("Test005 is OK!")
    }
    /**
     *  @author Mie Hayashi 2020/12/08
     * Check first six integer multiples of the cyclic number "142857" are not a prime number.
     */
    fun CheckerTest15(){
        val cnum = listOf(142857L, 285714L, 428571L, 571428L, 714285L, 857142L)
        for (num in cnum){
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test015 is OK!")
    }
    /**
    *   @author Alexandre Missler 2020/12/08
    * Check Euler-Jacobi psudoprime in prime base 97 for numbers lower than 10000
    */
        fun CheckerTest16() {
        val cnum = listOf(49L, 105L, 341L, 469L, 481L, 949L, 973L, 1065L, 2701L, 3283L, 3577L, 4187L, 4371L, 4705L, 6811L, 8023L, 8119L, 8911L, 9313L)
        for (num in cnum) {
            assertFalse { c_.isPrime(num) }
            assertFalse { c_.isPrimeRepunit(num.toBigInteger()) }
        }
        println("Test016 is OK!")
    }
}

fun main(args: Array<String>){
    var ctest = CheckerTest()
    ctest.CheckerTest01()
    ctest.CheckerTest02()
    ctest.CheckerTest03()
    ctest.CheckerTest04()
    ctest.CheckerTest05()
    ctest.CheckerTest06()
    ctest.CheckerTest08()
    ctest.CheckerTest09()
    ctest.CheckerTest10()
    ctest.CheckerTest11()
    ctest.CheckerTest12()
    ctest.CheckerTest13()
    ctest.CheckerTest14()
    ctest.binPowTest01()
    ctest.CheckerTest15()
    ctest.CheckerTest16()
}
