"""
Heap based (Max) Priority Queue
"""
class PriorityQueue:
    def __init__(self):
        self.heap = []
        self.n = 0
        self.heap.append(None)

    def insert(self, item):
        self.n += 1
        self.heap.insert(self.n, item)
        self.__swim(self.n)

    def pop(self):
        root = self.heap[1]
        self.__exch(1, self.n)
        self.heap.pop()
        self.n -= 1
        self.__sink(1)
        return root

    def empty(self):
        return 0 == self.n

    def size(self):
       return int(self.n)

    def __swim(self, key):
        """ reheapifying the tree """
        while key > 1 and self.heap[key] > self.heap[key // 2]:
            self.__exch(key, key // 2)
            key = key // 2

    def __sink(self, key):
        """ reheapifying the tree """
        while key * 2 < self.n:
            child = key * 2
            if ((self.n > child+1) and self.heap[child] < self.heap[child + 1]):
                child += 1
            self.__exch(key, child)
            key *= 2

    def __exch(self, a, b):
        """ exchange items a and b """
        tmp = self.heap[a]
        self.heap[a] = self.heap[b]
        self.heap[b] = tmp

if __name__ == "__main__":
    pq = PriorityQueue()
    print "size == 0: %s" % (pq.size() == 0)
    print "empty == True? %s" % (pq.empty() == True)
    pq.insert('p')
    pq.insert('q')
    pq.insert('e')
    print "extracted q? %s" % (pq.pop())
    pq.insert('x')
    pq.insert('a')
    pq.insert('m')
    print "extracted x? %s" % (pq.pop())
    pq.insert('p')
    pq.insert('l')
    pq.insert('e')
    print "extracted p? %s" % (pq.pop())

