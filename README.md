# Task for Big Data course
## Task 1
Need to create a file of 32-bit integers, 2 GB min size and calculate `min`, `max` and `sum` of elements.
Two versions are provided.
1. Simple sequential read which took 71.9515 sec on average of 5 measurements.
2. Multiprocessing with memory-mapped files which took 16.9473 sec on average of 10 measurements.

Second version is 4,24 times faster on 6 core i5-10400f CPU.