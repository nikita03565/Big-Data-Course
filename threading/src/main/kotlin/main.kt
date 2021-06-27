import kotlin.system.measureTimeMillis

fun countTime(fName: () -> Unit) {
    val elapsedTime = measureTimeMillis {
        fName()
    }
    println("Elapsed time $elapsedTime ms")
}

fun main() {
    countTime({ countSimple() })
    countTime({ countSimpleAlt() })
}
