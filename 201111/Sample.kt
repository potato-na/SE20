import kotlin.math.*
import java.math.BigInteger

fun _isPrime(x:Long): Boolean{
    if(x==1L) return false

    for (j in 2L..x / 2) {
            if (x % j == 0L) {
                return false
            }
    }
    return true
}

fun isPrimeBigInteger(x: BigInteger): Boolean{
    if (x == 1.toBigInteger()) {
        return false
    }
    if (x == 2.toBigInteger()) {
        return true
    }
    if (x % 2.toBigInteger() == 0.toBigInteger()) {
        return false
    }

    var j = 3.toBigInteger()
    while(j <= x) {
        if (x % j == 0.toBigInteger()) {
            return false
        }
        j += 2.toBigInteger()
    }
    return true
}

fun main() {
    var m = 0.toBigInteger()
    for (i in 1L..20L) {
        var n = m + 1.toBigInteger()
        println("%d %b".format(n, isPrimeBigInteger(n)))
        m = n * 10.toBigInteger()
    }
}
