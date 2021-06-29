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
    countTime ({ countAkka() })

    countTime({ countSimple(::getPrimeFactorsCountAlt) })
    countTime ({ RxFlowable(::getPrimeFactorsCountAlt)})
    countTime ({ countCompletableFuture(::getPrimeFactorsCountAlt) })
    countTime ({ countCompletableFutureGlobal(::getPrimeFactorsCountAlt) })
    countTime ({ countAkkaAlt() })
}


///////////////////////////////// Output /////////////////////////////////
/*
Simple result: 9029 with function: function getPrimeFactorsCount
Elapsed time 6787 ms

RxFlowable: 9029 with function: function getPrimeFactorsCount
Elapsed time 3220 ms

CompletableFuture result: 9029 with function function getPrimeFactorsCount
Elapsed time 3186 ms

CompletableFuture with Global var result: 9029 with function function getPrimeFactorsCount
Elapsed time 3171 ms

Akka result: 9029
Elapsed time 3936 ms

Simple result: 9029 with function: function getPrimeFactorsCountAlt
Elapsed time 53547 ms

RxFlowable: 9029 with function: function getPrimeFactorsCountAlt
Elapsed time 26241 ms

CompletableFuture result: 9029 with function function getPrimeFactorsCountAlt
Elapsed time 26344 ms

CompletableFuture with Global var result: 9029 with function function getPrimeFactorsCountAlt
Elapsed time 26463 ms

Akka result alt: 9029
Elapsed time 26493 ms

 */