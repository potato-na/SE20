import java.math.BigInteger
import kotlin.math.*
import kotlin.system.measureTimeMillis

fun main() {
    val checker = Checker()
    val time = measureTimeMillis {
        var n = 1.toBigInteger()

        for (i in 1L..317L) {
            if(checker.isPrime(i)){
                println("R_$i %b".format(checker.isPrimeRepunit(n)))
            }else{
                println("R_$i false")
            }
            n = n * 10.toBigInteger() + 1.toBigInteger()
        }
    }
    println("$time ms")
}