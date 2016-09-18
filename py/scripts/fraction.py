class Fraction:
    def __init__(self, top, bottom):
        """ constructor """
        self.num = top
        self.den = bottom

    def __str__(self):
        """ string representation """
        return str(self.num) + "/" + str(self.den)

    def __add__(self, other):
        """ add fractions """
        n = self.num * other.den + self.den * other.num
        d = self.den * other.den
        common = self.__gcd(n, d)

        return Fraction(n // common, d // common)

    def __gcd(self, m, n):
        while m % n != 0:
            old_m = m
            old_n = n
            m = old_n
            n = old_m % old_n

        return n

if __name__ == "__main__":
    f1 = Fraction(1, 4)
    print f1
    f2 = Fraction(1, 2)
    print f2
    r = f1 + f2
    print "%s + %s = %s" % (f1, f2, r)
