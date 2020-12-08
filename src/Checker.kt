import java.math.BigInteger


class Checker {
    fun checkNum(num: BigInteger): Pair<BigInteger, BigInteger>{
        var s = 0.toBigInteger()
        var d = num - 1.toBigInteger()

        while(d % 2.toBigInteger() == 0.toBigInteger()){
            s++
            d /= 2.toBigInteger()
        }

        return Pair(s, d)
    }

    fun binPow(x: BigInteger, y: BigInteger, n: BigInteger) : BigInteger {
        var result = 1.toBigInteger()
        var a = x % n
        var b = y
        while (b > 0.toBigInteger()){
            if (b and 1.toBigInteger() == 1.toBigInteger()){
                result *= a
                result %= n
            }
            b = b.shiftRight(1)
            a = (a * a) % n
        }
        return result
    }

    fun millerTest(d: BigInteger, num: BigInteger, a: BigInteger): Boolean {
        var x = binPow(a, d, num)
        if (x == 1.toBigInteger() || x == num - 1.toBigInteger()) return true

        var b = d
        while (b != num - 1.toBigInteger()) {
            x = (x.pow(2)) % num
            b *= 2.toBigInteger()
            if (x == 1.toBigInteger()) return false
            if (x == num - 1.toBigInteger()) return true
        }
        return false
    }

    fun isPrimeRepunit(x: BigInteger): Boolean{
        if (x <= 1.toBigInteger() || x == 4.toBigInteger()) return false
        if (x == 2.toBigInteger()) return true

        var a_sample = listOf(2, 3, 5, 7, 11, 13, 17, 31, 61, 73)

        var (s, d) = checkNum(x)

        for (a in a_sample) {
            if (a.toBigInteger() >= x) break
            if (!millerTest(d, x, a.toBigInteger())) return false
        }
        return true
    }

    fun isPrime(x:Long): Boolean{
        if(x==1L) return false

        var j = 2L
        while(j*j<=x){
            if (x % j == 0L) return false
            j++
        }
        return true
    }
}
