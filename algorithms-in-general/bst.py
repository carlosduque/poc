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
        self.n = 0

    def put(self, key, value):
        print "key: %s, value: %s" % (key, value)
        self.root = self.__put(self.root, key, value)

    def __put(self, root, k, v):
        print ":: root: %s, k: %s, v: %s" % (root, k, v)
        if root is None:
            return self.Node(k, v)

        if k == root.key:
            root.val = v

    def get(self, key):
        return nil

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
