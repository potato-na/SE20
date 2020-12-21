fun main() {
    var init = arrayOf<Long>(0x123, 0x234, 0x345, 0x456)
    var length = 4
    var max = 10
    var mt = MersenneTwister()
    mt.init_by_array(init, length)
    println("%d outputs of genrand_int32()".format(max))
    for (i in 0..max-1) {
        print("%10d ".format(mt.genrand_int32()))
        if (i % 5 == 4) println("")
    }

    println("%d outputs of genrand_int31()".format(max))
    for (i in 0..max-1) {
        print("%10d ".format(mt.genrand_int31()))
        if (i % 5 == 4) println("")
    }

    println("%d outputs of genrand_real1()".format(max))
    for (i in 0..max-1) {
        print("%10.8f ".format(mt.genrand_real1()))
        if (i % 5 == 4) println("")
    }

    println("%d outputs of genrand_real2()".format(max))
    for (i in 0..max-1) {
        print("%10.8f ".format(mt.genrand_real2()))
        if (i % 5 == 4) println("")
    }

    println("%d outputs of genrand_real3()".format(max))
    for (i in 0..max-1) {
        print("%10.8f ".format(mt.genrand_real3()))
        if (i % 5 == 4) println("")
    }

    println("%d outputs of genrand_res53()".format(max))
    for (i in 0..max-1) {
        print("%10.8f ".format(mt.genrand_res53()))
        if (i % 5 == 4) println("")
    }
}
