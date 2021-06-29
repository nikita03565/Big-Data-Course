import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.japi.pf.ReceiveBuilder
import java.io.File
import java.math.BigInteger

data class CountFactors(val countFactors: Int)

class MasterActor : AbstractActor() {
    var workersCount = 0
    var finished = 0
    var count = 0L
    var workers = Array<ActorRef>(workersCount) {
        context.actorOf(Props.create(SlaveActor::class.java))
    }

    override fun createReceive(): Receive =
        ReceiveBuilder()
            .match(File::class.java) { file ->
                val lines = file.readLines()
                workersCount = lines.size
                workers = Array(workersCount) { context.actorOf(Props.create(SlaveActor::class.java)) }

                for (i in 0 until workersCount) {
                    workers[i].tell(lines[i], self)
                }
            }
            .match(CountFactors::class.java) {
                count += it.countFactors
                finished++
                if (finished == workersCount) {
                    println("Akka result: $count")
                    context.system.terminate()
                }
            }
            .build()
}

class SlaveActor : AbstractActor() {
    override fun createReceive(): Receive =
        ReceiveBuilder()
            .match(String::class.java) {
                val countFactors: Int = getPrimeFactorsCount(BigInteger(it))
                sender.tell(CountFactors(countFactors), self)
            }
            .build()
}

fun countAkka() {
    val file = File(fileName)
    val actorSystem = ActorSystem.create("AkkaFactorization")
    val actorRef = actorSystem.actorOf(Props.create(MasterActor::class.java))
    actorRef.tell(file, actorRef)
    while (!actorRef.isTerminated) Thread.sleep(1)
}
