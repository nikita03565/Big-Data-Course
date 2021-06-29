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
    var res = 0
    var workers = Array<ActorRef>(workersCount) {
        context.actorOf(Props.create(SlaveActor::class.java))
    }

    override fun createReceive(): Receive =
        ReceiveBuilder()
            .match(File::class.java) { file ->
                val lines = file.readLines()
                val chunks = getChunksIndexes(lines.size)
                workersCount = chunks.size
                workers = Array(workersCount) { context.actorOf(Props.create(SlaveActor::class.java)) }

                for (i in 0 until chunks.size) {
                    val part = lines.slice(chunks[i].first..chunks[i].second)
                    workers[i].tell(part, self)
                }
            }
            .match(CountFactors::class.java) {
                res += it.countFactors
                finished++
                println(finished)
                if (finished == workersCount) {
                    println("Akka result: $res")
                    context.system.terminate()
                }
            }
            .build()
}

class SlaveActor : AbstractActor() {
    override fun createReceive(): Receive =
        ReceiveBuilder()
            .match(Iterable::class.java) {
                val numbers = it.map { l-> BigInteger(l as String) }
                var innerSum = 0
                for (n in numbers) innerSum += getPrimeFactorsCount(n)
                sender.tell(CountFactors(innerSum), self)
            }
            .build()
}

class MasterActorAlt : AbstractActor() {
    var workersCount = 0
    var finished = 0
    var res = 0
    var workers = Array<ActorRef>(workersCount) {
        context.actorOf(Props.create(SlaveActorAlt::class.java))
    }

    override fun createReceive(): Receive =
        ReceiveBuilder()
            .match(File::class.java) { file ->
                val lines = file.readLines()
                workersCount = lines.size
                workers = Array(workersCount) { context.actorOf(Props.create(SlaveActorAlt::class.java)) }

                for (i in 0 until workersCount) {
                    workers[i].tell(lines[i], self)
                }
            }
            .match(CountFactors::class.java) {
                res += it.countFactors
                finished++
                if (finished == workersCount) {
                    println("Akka result alt: $res")
                    context.system.terminate()
                }
            }
            .build()
}

class SlaveActorAlt : AbstractActor() {
    override fun createReceive(): Receive =
        ReceiveBuilder()
            .match(String::class.java) {
                val countFactors: Int = getPrimeFactorsCountAlt(BigInteger(it))
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

fun countAkkaAlt() {
    val file = File(fileName)
    val actorSystem = ActorSystem.create("AkkaFactorizationAlt")
    val actorRef = actorSystem.actorOf(Props.create(MasterActorAlt::class.java))
    actorRef.tell(file, actorRef)
    while (!actorRef.isTerminated) Thread.sleep(1)
}
