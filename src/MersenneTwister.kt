class MersenneTwister {
    var N = 624
    var M = 397
    var MATRIX_A = 0x9908b0dfL
    var UPPER_MASK = 0x80000000L
    var LOWER_MASK = 0x7fffffffL

    var mt = arrayOfNulls<Long>(N)
    var mti = N + 1

    fun init_genrand(s: Long){
        mt[0] = s and 0xffffffffL
        for (mti in 1 until N) {
            mt[mti] = (1812433253L * (mt[mti-1]!!.xor(mt[mti-1]!!.shr(30))) + mti)
            mt[mti] = mt[mti]!!.and(0xffffffffL)
        }
    }

    fun init_by_array(init_key: Array<Long> , key_length: Int){
        init_genrand(19650218L)
        var i = 1
        var j = 0
        var km = if (N > key_length) N else key_length

        for (k in km downTo 1) {
            mt[i] = (mt[i]!!.xor(mt[i-1]!!.xor(mt[i-1]!!.shr(30)) * 1664525L)) + init_key[j] + j
            mt[i] = mt[i]!!.and(0xffffffffL)

            i++
            if (i >= N){
                mt[0] = mt[N-1]
                i = 1
            }

            j++
            if (j>=key_length) j = 0
        }

        for (k in N-1 downTo 1) {
            mt[i] = (mt[i]!!.xor(mt[i-1]!!.xor(mt[i-1]!!.shr(30)) * 1566083941L)) - i
            mt[i] = mt[i]!!.and(0xffffffffL)

            i++
            if (i>=N){
                mt[0] = mt[N-1]
                i = 1
            }
        }

        mt[0] = 0x80000000L
    }

    fun genrand_int32():Long{
        var y: Long
        var mag01 = arrayOf<Long>(0x0L, MATRIX_A)

        if (mti >= N){
            if (mti == N+1) init_genrand(5489L)

            var kk = 0
            while (kk < N-M){
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
        y = y xor y.shr(11)
        y = y xor (y.shl(7) and 0x9d2c5680L)
        y = y xor (y.shl(15) and 0xefc60000L)
        y = y xor (y.shr(18))

        return y
    }

    fun genrand_real2():Double {
        return genrand_int32() * (1.0 / 4294967296.0)
    }
}
