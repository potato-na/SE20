import kotlin.math.*

fun isPrime(x:Long): Boolean{
    if(x==1L) return false

    for (j in 2L..x / 2) {
            if (x % j == 0L) {
                return false
            }
    }
    return true
}
fun main() {
    var n:Long = 0
    for (i in 0L..18L) {
        // 1+10+100+...の方法で1の連続数を作る
        var m = 10.0.pow(i.toDouble()).toLong()
        n = n+m
        println("%d %b".format(n, isPrime(n)))
        //if (isPrime(n)==true) println("%d".format(n))
    }
}
