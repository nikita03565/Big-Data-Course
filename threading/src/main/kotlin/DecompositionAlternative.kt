import java.io.File
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

fun printNumsFactors() {
    val lines: List<String> = File(fileName).readLines()
    lines.forEach { line -> println(getPrimeFactorsAlt(BigInteger(line))) }
}

fun main() {
    val primes =
        intArrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
    for (prime in primes) {
        val bigPow2 = bigTwo.pow(prime) - BigInteger.ONE
        println("2^${"%2d".format(prime)} - 1 = ${bigPow2.toString().padEnd(30)} => ${getPrimeFactorsAlt(bigPow2)}")
    }
}