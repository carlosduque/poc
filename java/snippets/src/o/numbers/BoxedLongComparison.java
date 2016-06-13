package o.numbers;

public class BoxedLongComparison {

    public static void main(String[] args) {
        Long one = new Long(1);
        Long uno = new Long(1);
        System.out.println("one == uno " + (one == uno) );

        long eins = 1L;
        long ichi = 1L;
        System.out.println("eins == ichi " + (eins == ichi));

        System.out.println("Comparing Integers: " + (new Integer(1) == new Integer(1)) );
        System.out.println("Comparing ints: " + (1 == 1) );
    }

}
