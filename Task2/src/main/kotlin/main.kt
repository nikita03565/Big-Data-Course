import kotlin.system.measureTimeMillis

fun countTime(fName: () -> Unit) {
    val elapsedTime = measureTimeMillis {
        fName()
    }
    println("Elapsed time $elapsedTime ms\n")
}

fun main() {
    countTime({ countSimple(::getPrimeFactorsCount) })
    countTime ({ RxFlowable(::getPrimeFactorsCount)})
    countTime ({ countCompletableFuture(::getPrimeFactorsCount) })
    countTime ({ countCompletableFutureGlobal(::getPrimeFactorsCount) })

    countTime({ countSimple(::getPrimeFactorsCountAlt) })
    countTime ({ RxFlowable(::getPrimeFactorsCountAlt)})
    countTime ({ countCompletableFuture(::getPrimeFactorsCountAlt) })
    countTime ({ countCompletableFutureGlobal(::getPrimeFactorsCountAlt) })
}


///////////////////////////////// Output /////////////////////////////////
/*
Simple result: 9029 with function: function getPrimeFactorsCount
Elapsed time 6865 ms

RxFlowable: 9029 with function: function getPrimeFactorsCount
Elapsed time 3230 ms

CompletableFuture result: 9029 with function function getPrimeFactorsCount
Elapsed time 3199 ms

CompletableFuture with Global var result: 9029 with function function getPrimeFactorsCount
Elapsed time 3146 ms

Simple result: 9029 with function: function getPrimeFactorsCountAlt
Elapsed time 55732 ms

RxFlowable: 9029 with function: function getPrimeFactorsCountAlt
Elapsed time 25733 ms

CompletableFuture result: 9029 with function function getPrimeFactorsCountAlt
Elapsed time 26271 ms

CompletableFuture with Global var result: 9029 with function function getPrimeFactorsCountAlt
Elapsed time 26296 ms
 */