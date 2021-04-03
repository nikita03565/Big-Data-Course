import numpy as np

count = 536870912  # Count of 32-bit integers that would take 2Gb of memory
f_name = "array"
max_value = 4294967295  # max value of uint32


if __name__ == "__main__":
    arr = np.random.randint(2, max_value, size=count, dtype=np.dtype('uint32').newbyteorder('B')).byteswap()
    with open(f_name, 'wb') as f:
        f.write(arr.data)
