import java.io.File
import java.math.BigInteger

fun countSimple() {
    var sum = 0
    val lines: List<String> = File(fileName).readLines()
    lines.forEach { line -> sum += getPrimeFactors(BigInteger(line)).size }
    println("Seq result: $sum")
}

fun countSimpleAlt() {
    var sum = 0
    val lines: List<String> = File(fileName).readLines()
    lines.forEach { line -> sum += getPrimeFactorsAlt(BigInteger(line)).size }
    println("Seq result alt: $sum")
}