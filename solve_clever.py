import mmap
import os
from multiprocessing import Pool

import numpy as np

from decorators import timing


file_name = "array"
size = os.path.getsize(file_name)  # in bytes

size_of_int = 4  # in bytes
max_threads = 12

# make sure that we won't split parts of one number between different chunks
chunk_size = size_of_int * ((size // max_threads) // size_of_int)
chunks = [[chunk_size * i, chunk_size * (i + 1)] for i in range(max_threads)]
# and make sure that we won't lose end of the file due to integer division
chunks[-1][1] = None


def perform_map(byte_array):
    array = np.frombuffer(byte_array, dtype=np.dtype('uint32').newbyteorder('B'))
    res_sum, res_max, res_min = 0, 0, array[0]

    for num in array:
        if num > res_max:
            res_max = num
        if num < res_min:
            res_min = num
        res_sum += num

    return res_sum, res_max, res_min


def perform_reduce(results):
    r_sum = sum(r[0] for r in results)
    r_max = max(r[1] for r in results)
    r_min = min(r[2] for r in results)
    print(f'Sum: {r_sum}')
    print(f'Max: {r_max}')
    print(f'Min: {r_min}')


@timing(10)
def solve_clever():
    with open(file_name, 'r+b') as f:
        with mmap.mmap(f.fileno(), length=0, access=mmap.ACCESS_READ) as mm:
            with Pool(max_threads) as pool:
                r = pool.map(perform_map, [mm[chunk[0]: chunk[1]] for chunk in chunks])
    perform_reduce(r)


if __name__ == "__main__":
    solve_clever()

# Output, just in case:
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.8244 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.6374 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 17.2618 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.7071 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.8776 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 17.1310 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.7390 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.9410 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 16.7617 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_clever' took: 17.5924 sec
# func: 'solve_clever' took: 16.9473 sec on average
