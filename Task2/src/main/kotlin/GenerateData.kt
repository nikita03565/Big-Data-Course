import java.io.File
import java.math.BigInteger
import java.util.*


val num = 2000
val bits = 48
val fileName = "out$`num`_$bits.txt"

fun generateData() {
    val random = Random()
    val nums = arrayListOf<BigInteger>()
    for (i in (0..num)) {
        nums.add(BigInteger(bits, random))
    }
    File(fileName).writeText(nums.joinToString("\n"))
}


fun main() {
    generateData()
}
