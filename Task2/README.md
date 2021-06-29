# Task 2

Generate text file containing 2000 48-bit random numbers, each number on new line.

Calculate the total number of prime factors present when factoring all numbers. For example, let there be only two
numbers:
6 and 8. 6 = 2 * 3, 8 = 2 * 2 * 2. Answer is 5.

Implement calculation:

1. using simple sequential algorithm
2. using multithreading
3. using Akka
4. using RxJava

# Hardware
i5-10400f CPU, 6 cores

# Results
Two different implementations of getPrimeFactorsCount were used:
1. Naive, simple loop through possible dividers that checks if number is dividable (called `getPrimeFactorsCountAlt`)
2. Optimized version using `isProbablePrime` method (called `getPrimeFactorsCount`)

Simple result: 9029 with function getPrimeFactorsCount \
Elapsed time 6787 ms

RxFlowable: 9029 with function getPrimeFactorsCount \
Elapsed time 3220 ms

CompletableFuture result: 9029 with function getPrimeFactorsCount\
Elapsed time 3186 ms

CompletableFuture with Global var result: 9029 with function getPrimeFactorsCount\
Elapsed time 3171 ms

Akka result: 9029 \
Elapsed time 3936 ms

Simple result: 9029 with function: function getPrimeFactorsCountAlt\
Elapsed time 53547 ms

RxFlowable: 9029 with function: function getPrimeFactorsCountAlt\
Elapsed time 26241 ms

CompletableFuture result: 9029 with function getPrimeFactorsCountAlt\
Elapsed time 26344 ms

CompletableFuture with Global var result: 9029 with function getPrimeFactorsCountAlt\
Elapsed time 26463 ms

Akka result alt: 9029\
Elapsed time 26493 ms

Any of multithreading implementations was 2 times faster than naive sequential one and there is
no much difference between different implementations. They all look like random deviations except for Akka.

These lines take 500ms to complete
```kotlin
val file = File(fileName)
val actorSystem = ActorSystem.create("AkkaFactorization")
val actorRef = actorSystem.actorOf(Props.create(MasterActor::class.java))
actorRef.tell(file, actorRef)
```  
