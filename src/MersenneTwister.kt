class MersenneTwister {
    /* Period parameters */
    var N = 624
    var M = 397
    var MATRIX_A = 0x9908b0dfL /* constant vector a */
    var UPPER_MASK = 0x80000000L /* most significant w-r bits */
    var LOWER_MASK = 0x7fffffffL /* least significant r bits */

    var mt = arrayOfNulls<Long>(N) /* the array for the state vector  */
    var mti = N + 1 /* mti==N+1 means mt[N] is not initialized */

    /* initializes mt[N] with a seed */
    fun init_genrand(s:Long){
        mt[0] = s and 0xffffffffL
        for (mti in 1..N-1) {
            mt[mti] = (1812433253L * (mt[mti-1]!! xor mt[mti-1]!!.shr(30)) + mti)
            /* See Knuth TAOCP Vol2. 3rd Ed. P.106 for multiplier. */
            /* In the previous versions, MSBs of the seed affect   */
            /* only MSBs of the array mt[].                        */
            /* 2002/01/09 modified by Makoto Matsumoto             */
            mt[mti] = (mt[mti]!! and 0xffffffffL)
            /* for >32 bit machines */
        }
    }

    /* initialize by an array with array-length */
    /* init_key is the array for initializing keys */
    /* key_length is its length */
    /* slight change for C++, 2004/2/26 */
    fun init_by_array(init_key:Array<Long>, key_length:Int){
        init_genrand(19650218L)
        var i = 1
        var j = 0
        var k = if (N>key_length) N else key_length
        for (ki in k downTo 1) {
            mt[i] = (mt[i]!! xor ((mt[i-1]!! xor mt[i-1]!!.shr(30)) * 1664525L)) + init_key[j] + j /* non linear */
            mt[i] = (mt[i]!! and 0xffffffffL) /* for WORDSIZE > 32 machines */
            i += 1
            j += 1
            if (i >= N) {
                mt[0] = mt[N-1]
                i = 1
            }
            if (j >= key_length) j = 0
        }
        for (ki in N-1 downTo 1) {
            mt[i] = (mt[i]!! xor ((mt[i-1]!! xor mt[i-1]!!.shr(30)) * 1566083941L)) - i /* non linear */
            mt[i] = (mt[i]!! and 0xffffffffL) /* for WORDSIZE > 32 machines */
            i += 1
            if (i >= N) {
                mt[0] = mt[N-1]
                i = 1
            }
        }
        mt[0] = 0x80000000L /* MSB is 1; assuring non-zero initial array */
    }

    /* generates a random number on [0,0xffffffff]-interval */
    fun genrand_int32():Long{
        var y: Long
        var mag01 = arrayOf<Long>(0x0L, MATRIX_A)
        /* mag01[x] = x * MATRIX_A  for x=0,1 */

        if (mti >= N) { /* generate N words at one time */
            var kk = 0

            if (mti == N+1)   /* if init_genrand() has not been called, */
                init_genrand(5489L) /* a default initial seed is used */

            while (kk < N-M) {
                y = ((mt[kk]!! and UPPER_MASK) or (mt[kk+1]!! and LOWER_MASK))
                mt[kk] = mt[kk+M]!! xor y.shr(1) xor mag01[(y and 0x1L).toInt()]
                kk += 1
            }
            while (kk < N-1) {
                y = (mt[kk]!! and UPPER_MASK) or (mt[kk+1]!! and LOWER_MASK)
                mt[kk] = mt[kk+(M-N)]!! xor y.shr(1) xor mag01[(y and 0x1L).toInt()]
                kk += 1
            }
            y = (mt[N-1]!! and UPPER_MASK) or (mt[0]!! and LOWER_MASK)
            mt[N-1] = mt[M-1]!! xor y.shr(1) xor mag01[(y and 0x1L).toInt()]
            mti = 0
        }

        y = mt[mti++]!!

        /* Tempering */
        y = (y xor y.shr(11))
        y = (y xor (y.shl(7) and 0x9d2c5680L))
        y = (y xor (y.shl(15) and 0xefc60000L))
        y = (y xor y.shr(18))

        return y
    }

/* generates a random number on [0,0x7fffffff]-interval */
    fun genrand_int31():Long{
        return (genrand_int32().shr(1).toLong())
    }

/* generates a random number on [0,1]-real-interval */
    fun genrand_real1():Double{
        return genrand_int32() * (1.0/4294967295.0)
        /* divided by 2^32-1 */
    }

/* generates a random number on [0,1)-real-interval */
    fun genrand_real2():Double{
        return genrand_int32()*(1.0/4294967296.0)
        /* divided by 2^32 */
    }

/* generates a random number on (0,1)-real-interval */
    fun genrand_real3():Double{
        return ((genrand_int32().toDouble()) + 0.5) * (1.0/4294967296.0)
        /* divided by 2^32 */
    }

/* generates a random number on [0,1) with 53-bit resolution*/
    fun genrand_res53():Double{
        var a = genrand_int32().shr(5)
        var b = genrand_int32().shr(6)
        return(a * 67108864.0 + b) * (1.0 / 9007199254740992.0)
    }
}