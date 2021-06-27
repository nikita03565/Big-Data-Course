import java.math.BigInteger

val bigTwo: BigInteger = BigInteger.valueOf(2L)
val bigThree: BigInteger = BigInteger.valueOf(3L)

fun getPrimeFactors(n: BigInteger): MutableList<BigInteger> {
    val factors = mutableListOf<BigInteger>()
    if (n < bigTwo) return factors
    if (n.isProbablePrime(20)) {
        factors.add(n)
        return factors
    }
    var factor = bigTwo
    var nn = n
    while (true) {
        if (nn % factor == BigInteger.ZERO) {
            factors.add(factor)
            nn /= factor
            if (nn == BigInteger.ONE) return factors
            if (nn.isProbablePrime(20)) factor = nn
        } else if (factor >= bigThree) factor += bigTwo
        else factor = bigThree
    }
}

fun getPrimeFactorsCount(n: BigInteger): Int {
    var factorsCount = 0
    if (n < bigTwo) return 0
    if (n.isProbablePrime(20)) {
        return 1
    }
    var factor = bigTwo
    var nn = n
    while (true) {
        if (nn % factor == BigInteger.ZERO) {
            factorsCount += 1
            nn /= factor
            if (nn == BigInteger.ONE) return factorsCount
            if (nn.isProbablePrime(20)) factor = nn
        } else if (factor >= bigThree) factor += bigTwo
        else factor = bigThree
    }
}
