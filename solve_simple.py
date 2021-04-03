import numpy as np

from decorators import timing

file_name = "array"


def read_array():
    with open(file_name, 'r+b') as f:
        content = f.read()
    return np.frombuffer(content, dtype=np.dtype('uint32').newbyteorder('B'))


@timing(5)
def solve_simple():
    array = read_array()
    res_sum, res_max, res_min = 0, 0, array[0]

    for num in array:
        if num > res_max:
            res_max = num
        if num < res_min:
            res_min = num
        res_sum += num

    print(f'Sum: {res_sum}')
    print(f'Max: {res_max}')
    print(f'Min: {res_min}')


if __name__ == "__main__":
    solve_simple()

# Output, just in case:
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_simple' took: 72.2614 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_simple' took: 71.8139 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_simple' took: 71.5230 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_simple' took: 71.8098 sec
# Sum: 1152898469225891636
# Max: 4294967287
# Min: 16
# func: 'solve_simple' took: 72.3496 sec
# func: 'solve_simple' took: 71.9515 sec on average
