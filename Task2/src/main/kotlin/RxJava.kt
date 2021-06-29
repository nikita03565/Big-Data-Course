import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.math.BigInteger


fun RxFlowable(func: (BigInteger) -> Int): Int {
    val lines = File(fileName).readLines()
    var res = 0
    Flowable.fromIterable(lines)
        .onBackpressureBuffer()
        .parallel()
        .runOn(Schedulers.computation())
        .map { x -> func(BigInteger(x)) }
        .sequential()
        .blockingSubscribe({ x -> res += x })

    println("RxFlowable: $res with function: $func")
    return res
}
