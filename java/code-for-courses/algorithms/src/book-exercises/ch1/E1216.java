/**
 * 1.2.16 Rational numbers. Implement an immutable data type Rational for rational
 * numbers that supports addition, subtraction, multiplication, and division.
 *
 * #### Public class Rational
 * |          |                                             | Description                     |
 * |----------|---------------------------------------------|---------------------------------|
 * |          |  Rational(int numerator, int denominator)   |                                 |
 * | Rational |  plus(Rational b)                           | sum of this number and b        |
 * | Rational |  minus(Rational b)                          | difference of this number and b |
 * | Rational |  times(Rational b)                          | product of this number and b    |
 * | Rational |  divides(Rational b)                        | quotient of this number and b   |
 * | boolean  |  equals(Rational that)                      | is this number equal to that ?  |
 * | String   |  toString()                                 | string representation           |
 *
 * You do not have to worry about testing for overflow (see Exercise 1.2.17), but use as
 * instance variables two long values that represent the numerator and denominator to
 * limit the possibility of overflow. Use Euclid’s algorithm (see page 4) to ensure that the
 * numerator and denominator never have any common factors. Include a test client that
 * exercises all of your methods.
 *
 **/

class Rational {
    private long num = 0;
    private long den = 0;
    public Rational(int numerator, int denominator) {
        num = numerator;
        den = denominator;
    }

    public Rational plus(Rational b) {
        return new Rational((int)((num * b.getDenominator()) + (b.getNumerator() * den)), (int)(den * b.getDenominator()));
    }

    public Rational minus(Rational b) {
        return null;
    }

    public Rational times(Rational b) {
        return new Rational((int) (num * b.getNumerator()), (int)(den * b.getDenominator()));
    }

    public Rational divides(Rational b) {
        return null;
    }

    public boolean equals(Rational that) {
        return num == that.getNumerator() && den == that.getDenominator();
    }

    public String toString() {
        return String.format("%d / %d", num, den);
    }

    public long getNumerator() {
        return num;
    }

    public long getDenominator() {
        return den;
    }
}

public class E1216 {
    public static void main(String... args) {
        Rational x = new Rational(3, 4);
        Rational y = new Rational(2, 3);
        StdOut.printf("x: %s \n", x);
        StdOut.printf("y: %s \n", y);
        StdOut.printf("x + y: %s \n", x.plus(y));
        StdOut.printf("x - y: %s \n", x.minus(y));
        StdOut.printf("x * y: %s \n", x.times(y));
        StdOut.printf("x / y: %s \n", x.divides(y));
    }
}
