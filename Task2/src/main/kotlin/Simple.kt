import java.io.File
import java.math.BigInteger

fun countSimple(func: (BigInteger) -> Int) {
    var sum = 0
    val lines: List<String> = File(fileName).readLines()
    lines.forEach { line -> sum += func(BigInteger(line)) }
    println("Simple result: $sum with function: $func")
}