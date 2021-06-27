from functools import wraps
from time import time


def timing(n):
    def inner(f):
        @wraps(f)
        def wrap(*args, **kwargs):
            result = None
            elapsed_times = []
            for _ in range(n):
                t_start = time()
                r = f(*args, **kwargs)
                t_end = time()
                elapsed = t_end - t_start
                elapsed_times.append(elapsed)
                print('func: %r took: %2.4f sec' % (f.__name__, elapsed))
                if result is None:
                    result = r
            average = sum(elapsed_times) / len(elapsed_times)
            print('func: %r took: %2.4f sec on average' % (f.__name__, average))
            return result
        return wrap
    return inner
