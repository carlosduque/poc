/**
 * 1.1.21 Write a program that reads in lines from standard input with each line containing a name and two integers and
 * then uses printf() to print a table with a column of the names, the integers, and the result of dividing the first by
 * the second, accurate to three decimal places. You could use a program like this to tabulate batting averages for
 * baseball players or grades for students.
 */
public class E1121 {

    public static void main(String[] args) {
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            String[] token = in.readLine().split(" ");
            double a = Double.parseDouble(token[1]);
            double b = Double.parseDouble(token[2]);
            StdOut.printf(" > %s %1.3f %1.3f %1.3f \n", token[0], a, b, b/a);
        }

    }

}
