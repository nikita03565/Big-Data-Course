import kotlin.system.measureTimeMillis

fun countTime(fName: () -> Unit) {
    val elapsedTime = measureTimeMillis {
        fName()
    }
    println("Elapsed time $elapsedTime ms\n")
}

fun main() {
    countTime({ countSimple(::getPrimeFactorsCount) })
    countTime({ countSimple(::getPrimeFactorsCountAlt) })

    countTime ({ countCompletableFuture(::getPrimeFactorsCount) })
    countTime ({ countCompletableFutureGlobal(::getPrimeFactorsCount) })
    countTime ({ countCompletableFuture(::getPrimeFactorsCountAlt) })
    countTime ({ countCompletableFutureGlobal(::getPrimeFactorsCountAlt) })
}
