fun main() {
    val mt = MersenneTwister()
    var init = arrayOf<Long>(0x123, 0x234, 0x345, 0x456)
    var length = 4
    mt.init_by_array(init, length)
    var MAX_NUM = 100

    println("%d outputs of genrand_int32()".format(MAX_NUM))
    for (i in 0 until MAX_NUM) {
        print("%10d ".format(mt.genrand_int32()))
        if (i%5 == 4) print("\n")
    }

    println("\n%d outputs of genrand_real2()".format(MAX_NUM))
    for (i in 0 until MAX_NUM) {
        print("%10.8f ".format(mt.genrand_real2()))
        if (i%5 == 4) print("\n")
    }
}
