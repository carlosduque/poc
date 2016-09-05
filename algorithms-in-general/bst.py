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
    Nodes for the binary tree.
    """
    class Node:
        def __init__(self, key, value):
            self.key = key
            self.val = value
            self.right = None
            self.left = None
            self.count = 1
        def __str__(self):
            return "(%s=%s, count=%s)" % (self.key, self.val, self.count)

    def __init__(self):
        self.root = None

    def put(self, key, value):
        """
        Put a new key=value pair in the tree.
        :param key: the key to insert/update
        :param value: the value to store
        :return: nothing
        """
        self.root = self._put(self.root, key, value)

    def _put(self, root, k, v):
        """
        Traverses node to node starting at the root, going left or right according to the key
        until it finds a null spot where it creates the new node and takes it's place.
        :param root: The root of the tree or current subtree
        :param k: the key to insert
        :param v: the value to store
        :return: the node's parent
        """
        if root is None:
            new_node = self.Node(k, v)
            return new_node

        node = root
        while node is not None:
            if k < node.key:
                node.left = self._put(node.left, k, v)
            elif k > node.key:
                node.right = self._put(node.right, k, v)
            else:
                node.val = v
            node.count = 1 + self._size(node.left) + self._size(node.right)
            return node

    def get(self, k):
        """
        Retrieve a value stored with the given key.
        :param k: The key used to search
        :return: the value stored with the given key
        """
        node = self.root
        while node is not None:
            if k < node.key:
                node = node.left
            elif k > node.key:
                node = node.right
            else:
                return node.val
        return None

    def delete(self, key):
        pass

    def contains(self, key):
        """
        Check if the given key exists in the tree
        :param key: the key to search for
        :return: True if the key exists in the tree, False otherwise
        """
        if key is None:
            return None
        return self.get(key) is not None

    def is_empty(self):
        """
        Check if the tree is empty
        :return: True if the tree is empty, False otherwise
        """
        return self.root is None

    def size(self):
        """
        Return the size of the tree.
        :return: The tree's size
        """
        return self._size(self.root)

    def _size(self, node):
        """
        Return the current node's subtree size
        :param node: The node to check
        :return: zero if the node is null, the size of the subtree otherwise
        """
        if node is None:
            return 0
        return node.count

    def keys(self):
        queue = []
        self._traverse(self.root, queue)
        return queue

    def _traverse(self, node, queue):
        if node is None:
            return
        self._traverse(node.left, queue)
        queue.append(node.key)
        self._traverse(node.right, queue)

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