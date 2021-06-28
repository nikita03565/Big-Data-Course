import java.io.File
import java.math.BigInteger
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger


fun doMapGlobal(lines: List<String>, func: (BigInteger) -> Int, res: AtomicInteger) {
    val numbers = lines.map { BigInteger(it) }
    for (n in numbers) res.addAndGet(func(n))
}

fun countCompletableFutureGlobal(func: (BigInteger) -> Int) {
    var res = AtomicInteger()
    val lines: List<String> = File(fileName).readLines()
    val chunks = getChunksIndexes(lines.size)
    val futures = arrayListOf<CompletableFuture<Unit>>()
    for (chunk in chunks) {
        val part = lines.slice(chunk.first..chunk.second)
        futures.add(CompletableFuture.supplyAsync { doMapGlobal(part, func, res) })
    }
    for (f in futures) f.get()

    println("CompletableFuture with Global var result: $res with function $func")
}

fun main() {
    countCompletableFutureGlobal(::getPrimeFactorsCount)
    countCompletableFutureGlobal(::getPrimeFactorsCountAlt)
}