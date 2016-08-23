"""
Binary Search Tree
"""
class BST:
    """
    Nodes for the binary tree
    """
    class Node:
        def __init__(self, key, value):
            self.key = key
            self.val = value
            self.right = None
            self.left = None

    def __init__(self):
        self.root = None

    def put(self, key, value):
        self.root = self.__put(self.root, key, value)
        print "self.root: %s" % (self.root)

    def __put(self, root, k, v):
        if root is None:
            print "node(%s, %s)" % (k, v)
            return self.Node(k, v)

        x = root
        while x is not None:
            if k < x.key:
                print "k:%s < x.key:%s" % (k, x.key)
                x.left = self.__put(x.left, k, v)
            elif k > x.key:
                print "k:%s > x.key:%s" % (k, x.key)
                x.right = self.__put(x.right, k, v)
            else:
                print "%s = %s" % (k, v)
                x.val = v
            return x

    def get(self, k):
        x = self.root
        while x is not None:
            if k < x.key:
                x = x.left
            elif k > x.key:
                x = x.right
            else:
                return x.val

        return None

    def delete(self, key):
        pass

    def contains(self, key):
        return nil

    def isEmpty(self):
        return self.n == 0

    def size(self):
        return self.n

    def keys(self):
        return None
