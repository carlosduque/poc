"""
Binary Search Tree
from bst import BST()
bst = BST();
bst.put('m', 'magneto');
bst.put('d', 'daredevil');
bst.put('n', 'nightcrawler');
bst.put('f', 'flash');
bst.put('b', 'batman')
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
            self.size = 0
        def __str__(self):
            return "%s=%s, sz=%s" % (self.key, self.val, self.size)

    def __init__(self):
        self.root = None

    def put(self, key, value):
        self.root = self._put(self.root, key, value)

    def _put(self, root, k, v):
        if root is None:
            return self.Node(k, v)

        x = root
        while x is not None:
            if k < x.key:
                x.left = self._put(x.left, k, v)
            elif k > x.key:
                x.right = self._put(x.right, k, v)
            else:
                x.val = v
            x.size = 1 + self._size(x.left) + self._size(x.right)
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
        if key is None:
            return None
        return self.get(key) is not None

    def is_empty(self):
        return self.root is None

    def size(self):
        return self._size(self.root)

    def _size(self, node):
        if node is None:
            return 0
        return node.size

    def curr_root(self):
        return str(self.root)

    def keys(self):
        return None

    """
    Minimum and maximum. If the left link of the root is null, the smallest key in a
    BST is the key at the root; if the left link is not null, the smallest key in the BST
    is the smallest key in the subtree rooted at the node referenced by the left link.
    Finding the maximum key is similar, moving to the right instead of to the left.
    """
    def min(self):
        pass

    def max(self):
        pass


    """
    Floor and ceiling. If a given key key is less than the key at the root of a BST,
    then the floor of key (the largest key in the BST less than or equal to key) must
    be in the left subtree. If key is greater than the key at the root, then the floor
    of key could be in the right subtree, but only if there is a key smaller than or equal
    to key in the right subtree; if not (or if key is equal to the key at the root) then
    the key at the root is the floor of key. Finding the ceiling is similar, interchanging right and left.
    """
    def floor(self, key):
        pass

    def ceiling(self, key):
        pass

    """
    Selection. Suppose that we seek the key of rank k (the key such that precisely k
    other keys in the BST are smaller). If the number of keys t in the left subtree is
    larger than k, we look (recursively) for the key of rank k in the left subtree;
    if t is equal to k, we return the key at the root; and if t is smaller than k, we
    look (recursively) for the key of rank k - t - 1 in the right subtree.
    """
    def rank(self, key):
        pass