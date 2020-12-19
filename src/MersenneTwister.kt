// original c script: http://www.math.sci.hiroshima-u.ac.jp/m-mat/MT/MT2002/CODES/mt19937ar.c

class MersenneTwister(init_key: Array<Long>) {
    val N = 624
    val M = 397
    val MATRIX_A = 0x9908b0dfL
    val UPPER_MASK = 0x80000000L
    val LOWER_MASK = 0x7fffffffL

    var mt = arrayOfNulls<Long>(N)
    var mti = N + 1

    init {
        init_by_array(init_key)
    }

    // initialize mt[N] with a seed
    fun init_genrand(s: Long) {
        mt[0] = s and 0xffffffffL
        for (mti in 1 until N) {
            mt[mti] = (1812433253L * (mt[mti-1]!!.xor(mt[mti-1]!!.shr(30))) + mti)
            mt[mti] = mt[mti]!!.and(0xffffffffL)
        }
    }

    // initialize by an array
    // init_key is the array for initializing keys
    // argument key_length in original src is got by func size
    fun init_by_array(init_key: Array<Long>) {
        val key_length =  init_key.size
        init_genrand(19650218L)
        var i = 1
        var j = 0
        var k = if (N > key_length) N else key_length

        for (_k in k downTo 1) {
            mt[i] = (mt[i]!!.xor(mt[i-1]!!.xor(mt[i-1]!!.shr(30)) * 1664525L)) + init_key[j] + j
            mt[i] = mt[i]!!.and(0xffffffffL)
            i++
            j++
            if (i >= N) {
                mt[0] = mt[N - 1]
                i = 1
            }
            if (j >= key_length) {
                j = 0
            }
        }

        for (_k in N-1 downTo 1) {
            mt[i] = (mt[i]!!.xor(mt[i-1]!!.xor(mt[i-1]!!.shr(30)) * 1566083941L)) - i
            mt[i] = mt[i]!!.and(0xffffffffL)
            i++
            if (i >= N) {
                mt[0] = mt[N-1]
                i = 1
            }
        }
        mt[0] = 0x80000000L
    }

    // geneates a random number on [0, 0
    fun genrand_int32():Long {
        var y: Long
        var mag01 = arrayOf<Long>(0x0L, MATRIX_A)

        // generate N words at one time
        if (mti >= N) {
            var kk = 0
            // if init_genrand() hasnot been called
            if (mti == N+1) {
                init_genrand(5489L)
            }
            while (kk < N-M) {
                y = (mt[kk]!!.and(UPPER_MASK) or (mt[kk+1]!!.and(LOWER_MASK)))
                mt[kk] = mt[kk+M]!!.xor(y shr 1).xor(mag01[y.and(0x1L).toInt()])
                kk++
            }
            while (kk < N-1){
                y = (mt[kk]!!.and(UPPER_MASK) or (mt[kk+1]!!.and(LOWER_MASK)))
                mt[kk] = mt[kk+(M-N)]!!.xor(y shr 1).xor(mag01[y.and(0x1L).toInt()])
                kk++
            }
            y = (mt[N-1]!!.and(UPPER_MASK)) or (mt[0]!!.and(LOWER_MASK))
            mt[N-1] = mt[M-1]!!.xor(y shr 1).xor(mag01[y.and(0x1L).toInt()])
            mti = 0

        }

        y = mt[mti++]!!

        // Tempering
        y = y xor y.shr(11)
        y = y xor (y.shl(7) and 0x9d2c5680L)
        y = y xor (y.shl(15) and 0xefc60000L)
        y = y xor (y.shr(18))

        return y
    }

    // generates a random number on [0, 1)-real-interval
    fun genrand_real2(): Double {
        return genrand_int32() * (1.0 / 4294967296.0) // divided by 2^32
    }

    // generates a random number on (0, 1)-real-interval
    fun genrand_real3(): Double {
        return (genrand_int32() + 0.5) * (1.0 / 4294967296.0)
    }

}