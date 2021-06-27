import java.math.BigInteger
import java.util.*

fun getPrimeFactorsAlt(n: BigInteger): LinkedList<*> {
    var n = n
    val two = BigInteger.valueOf(2)
    val fs: LinkedList<BigInteger> = LinkedList<BigInteger>()
    require(n.compareTo(two) >= 0) { "must be greater than one" }
    while (n.mod(two) == BigInteger.ZERO) {
        fs.add(two)
        n = n.divide(two)
    }
    if (n.compareTo(BigInteger.ONE) > 0) {
        var f = BigInteger.valueOf(3)
        while (f.multiply(f).compareTo(n) <= 0) {
            if (n.mod(f) == BigInteger.ZERO) {
                fs.add(f)
                n = n.divide(f)
            } else {
                f = f.add(two)
            }
        }
        fs.add(n)
    }
    return fs
}

fun getPrimeFactorsCountAlt(n: BigInteger): Int {
    var n = n
    val two = BigInteger.valueOf(2)
    var fs = 0
    require(n.compareTo(two) >= 0) { "must be greater than one" }
    while (n.mod(two) == BigInteger.ZERO) {
        fs += 1
        n = n.divide(two)
    }
    if (n.compareTo(BigInteger.ONE) > 0) {
        var f = BigInteger.valueOf(3)
        while (f.multiply(f).compareTo(n) <= 0) {
            if (n.mod(f) == BigInteger.ZERO) {
                fs += 1
                n = n.divide(f)
            } else {
                f = f.add(two)
            }
        }
        fs += 1
    }
    return fs
}