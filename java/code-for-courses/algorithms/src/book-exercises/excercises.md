# Chapter 1
## Section 1
1.1.3 Write a program that takes three integer command-line arguments and prints equal if all three are equal, and not
      equal otherwise.

1.1.5 Write a code fragment that prints true if the double variables x and y are both strictly between 0 and 1 and
      false otherwise.

1.1.6 What does the following program print?

      int f = 0;
       int g = 1;
       for (int i = 0; i <= 15; i++) {
          StdOut.println(f);
          f = f + g;
          g = f - g;
       }

1.1.9 Write a code fragment that puts the binary representation of a positive integer N
into a String s.
Solution: Java has a built-in method Integer.toBinaryString(N) for this job, but the point of the exercise is to see how
such a method might be implemented. Here is a particularly concise solution:

    String s = "";
    for (int n = N; n > 0; n /= 2)
        s = (n % 2) + s;

1.1.11 Write a code fragment that prints the contents of a two-dimensional boolean array, using * to represent true and
a space to represent false. Include row and column numbers.

1.1.13 Write a code fragment to print the transposition (rows and columns changed) of a two-dimensional array with M
rows and N columns.

1.1.14 Write a static method lg() that takes an int value N as argument and returns the largest int not larger than the
base-2 logarithm of N. Do not use Math.

1.1.15 Write a static method histogram() that takes an array a[] of int values and an integer M as arguments and returns
an array of length M whose ith entry is the number of times the integer i appeared in the argument array. If the
values in a[] are all between 0 and M–1, the sum of the values in the returned array should be equal to a.length.

1.1.16 Give the value of exR1(6):

    public static String exR1(int n) {
        if (n <= 0) return "";
        return exR1(n-3) + n + exR1(n-2) + n;
    }

1.1.18 Consider the following recursive function:
    public static int mystery(int a, int b)
    {
        if (b == 0) return 0;
        if (b % 2 == 0) return mystery(a+a, b/2);
        return mystery(a+a, b/2) + a;
    }
What are the values of mystery(2, 25) and mystery(3, 11)? Given positive integers
a and b, describe what value mystery(a, b) computes. Answer the same question, but
replace + with * and replace return 0 with return 1.

1.1.19 Run the following program on your computer:

    public class Fibonacci {

        public static long F(int N) {
            if (N == 0) return 0;
            if (N == 1) return 1;
            return F(N-1) + F(N-2);
        }

        public static void main(String[] args) {
            for (int N = 0; N < 100; N++)
                StdOut.println(N + " " + F(N));
        }
    }

What is the largest value of N for which this program takes less 1 hour to compute the value of F(N)? Develop a better
implementation of F(N) that saves computed values in an array.

1.1.20 Write a recursive static method that computes the value of ln(N!)

1.1.21 Write a program that reads in lines from standard input with each line containing a name and two integers and
then uses printf() to print a table with a column of the names, the integers, and the result of dividing the first by
the second, accurate to three decimal places. You could use a program like this to tabulate batting averages for
baseball players or grades for students.

1.1.22 Write a version of BinarySearch that uses the recursive rank() given on page 25 and traces the method calls.
Each time the recursive method is called, print the argument values lo and hi, indented by the depth of the recursion.
Hint: Add an argument to the recursive method that keeps track of the depth.

1.1.24 Give the sequence of values of p and q that are computed when Euclid’s algorithm is used to compute the
greatest common divisor of 105 and 24. Extend the code given on page 4 to develop a program Euclid that takes two
integers from the command line and computes their greatest common divisor, printing out the two arguments for each call
on the recursive method. Use your program to compute the greatest common divisor or 1111111 and 1234567.

1.1.27 Binomial distribution. Estimate the number of recursive calls that would be used by the code

    public static double binomial(int N, int k, double p) {
        if ((N == 0) || (k < 0)) return 1.0;
        return (1.0 - p)*binomial(N-1, k, p) + p*binomial(N-1, k-1, p);
    }

to compute binomial(100, 50). Develop a better implementation that is based on
saving computed values in an array.

1.1.29 Equal keys. Add to BinarySearch a static method rank() that takes a key and
a sorted array of int values (some of which may be equal) as arguments and returns
the number of elements that are smaller than the key and a similar method count()
that returns the number of elements equal to the key. Note : If i and j are the
values returned by rank(key, a) and count(key,a) respectively, then a[i..i+j-1] are the values in the array that are
equal to key.

1.1.30 Array exercise. Write a code fragment that creates an N-by-N boolean array a[][] such that a[i][j] is true if
i and j are relatively prime (have no common factors), and false otherwise.

1.1.31 Random connections. Write a program that takes as command-line arguments an integer N and a double value
p (between 0 and 1), plots N equally spaced dots of size .05 on the circumference of a circle, and then, with
probability p for each pair of points, draws a gray line connecting them.

1.1.33 Matrix library. Write a library Matrix that implements the following API:

#### Public class Matrix
|       |                                             | Description           |
|-------|---------------------------------------------|-----------------------|
|static | double dot(double[] x, double[] y)          | vector dot product    |
|static | double[][] mult(double[][] a, double[][] b) | matrix-matrix product |
|static | double[][] transpose(double[][] a)          | transpose             |
|static | double[] mult(double[][] a, double[] x)     | matrix-vector product |
|static | double[] mult(double[] y, double[][] a)     | vector-matrix product |

Develop a test client that reads values from standard input and tests all the methods.

1.1.35 Dice simulation. The following code computes the exact probability distribution for the sum of two dice:

    int SIDES = 6;
    double[] dist = new double[2*SIDES+1];
    for (int i = 1; i <= SIDES; i++)
        for (int j = 1; j <= SIDES; j++)
            dist[i+j] += 1.0;
    for (int k = 2; k <= 2*SIDES; k++)
        dist[k] /= 36.0;

The value dist[i] is the probability that the dice sum to k. Run experiments to validate this calculation simulating N
dice throws, keeping track of the frequencies of occurrence of each value when you compute the sum of two random
integers between 1 and 6. How large does N have to be before your empirical results match the exact results to three
decimal places?

1.1.38 Binary search versus brute-force search. Write a program BruteForceSearch
that uses the brute-force search method given on page 48 and compare its running
time on your computer with that of BinarySearch for largeW.txt and largeT.txt.

1.1.39 Random matches. Write a BinarySearch client that takes an int value T as command-line argument and runs T trials
of the following experiment for N = 103, 104, 105, and 106: generate two arrays of N randomly generated positive
six-digit int values, and find the number of values that appear in both arrays. Print a table giving the average value
of this quantity over the T trials for each value of N.

## Section 2
1.2.6 A string s is a circular rotation of a string t if it matches when the characters
are circularly shifted by any number of positions; e.g., ACTGACG is a circular shift of
TGACGAC, and vice versa. Detecting this condition is important in the study of genomic
sequences. Write a program that checks whether two given strings s and t are circular
shifts of one another. Hint : The solution is a one-liner with indexOf(), length(), and
string concatenation.

1.2.16 Rational numbers. Implement an immutable data type Rational for rational
numbers that supports addition, subtraction, multiplication, and division.

#### Public class Rational
|          |                                             | Description                     |
|----------|---------------------------------------------|---------------------------------|
|          |  Rational(int numerator, int denominator)   |                                 |
| Rational |  plus(Rational b)                           | sum of this number and b        |
| Rational |  minus(Rational b)                          | difference of this number and b |
| Rational |  times(Rational b)                          | product of this number and b    |
| Rational |  divides(Rational b)                        | quotient of this number and b   |
| boolean  |  equals(Rational that)                      | is this number equal to that ?  |
| String   |  toString()                                 | string representation           |

You do not have to worry about testing for overflow (see Exercise 1.2.17), but use as
instance variables two long values that represent the numerator and denominator to
limit the possibility of overflow. Use Euclid’s algorithm (see page 4) to ensure that the
numerator and denominator never have any common factors. Include a test client that
exercises all of your methods.

## Section 3
