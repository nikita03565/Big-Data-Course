import java.io.File
import java.math.BigInteger
import java.util.concurrent.CompletableFuture


fun getChunksIndexes(numLines: Int): ArrayList<Pair<Int, Int>> {
    val cores = Runtime.getRuntime().availableProcessors()
    val chunkSize = numLines / cores
    val chunks = arrayListOf<Pair<Int, Int>>()
    for (i in 0 until cores) {
        val rightBound = if (i == cores - 1) numLines else (i + 1) * chunkSize
        chunks.add(Pair(chunkSize * i, rightBound - 1))
    }
    return chunks
}

fun doMap(lines: List<String>, func: (BigInteger) -> Int): Int {
    val numbers = lines.map { BigInteger(it) }
    var innerSum = 0
    for (n in numbers) innerSum += func(n)
    return innerSum
}

fun countCompletableFuture(func: (BigInteger) -> Int) {
    val lines: List<String> = File(fileName).readLines()
    val chunks = getChunksIndexes(lines.size)
    val futures = arrayListOf<CompletableFuture<Int>>()
    for (chunk in chunks) {
        val part = lines.slice(chunk.first..chunk.second)
        futures.add(CompletableFuture.supplyAsync { doMap(part, func) })
    }

    var sum = 0
    for (f in futures) sum += f.get()

    println("CompletableFuture result: $sum with function $func")
}

fun main() {
    countCompletableFuture(::getPrimeFactorsCount)
    countCompletableFuture(::getPrimeFactorsCountAlt)
}