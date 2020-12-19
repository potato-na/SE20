// original c script: http://www.math.sci.hiroshima-u.ac.jp/m-mat/MT/MT2002/CODES/mt19937ar.c

fun main() {
    var init = arrayOf<Long>(0x123, 0x234, 0x345, 0x456)
    val mt = MersenneTwister(init_key = init)

    println("100 outputs of genrand_int32()")
    for (i in 0 until 100) {
        print("%10d ".format(mt.genrand_int32()))
        if (i%5 == 4) print("\n")
    }

    println("100 outputs of genrand_real2()")
    for (i in 0 until 100) {
        print("%10d ".format(mt.genrand_real2()))
        if (i%5 == 4) print("\n")
    }

    println("100 outputs of genrand_real3()")
    for (i in 0 until 100) {
        print("%10d ".format(mt.genrand_real3()))
        if (i%5 == 4) print("\n")
    }
}