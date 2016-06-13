package o.binary;

public class BitDemo {

    public BitDemo() {
    }

    public static void main(String[] args) {
        byte a = 2;  // 0010
        byte b = 4;  // 0100
        byte c = 13; // 1101

        System.out.format("Given: a=%d, b=%d, c=%d%n", a, b, c);
        System.out.println("= Bitwise operators");
        System.out.println("~a =      " + (~a)); // -3 = 1101 (2's complement + 1 = -3)
        System.out.println("~c =      " + (~c)); // -14 = 1110 (2's complement + 1 = -14)
        System.out.println("b & c =   " + (b & c)); // 4 = 0100
        System.out.println("a | b =   " + (a | b)); // 6 = 0110
        System.out.println("b ^ c =   " + (b ^ c)); // 9 = 1001
        System.out.println("= Bit shifts");
        // In these operations the digits are moved, or shifted, to the left or right.
        /* In an arithmetic shift, the bits that are shifted out of either end are discarded.
         * In a left arithmetic shift, zeros are shifted in on the right; in a right
         * arithmetic shift, the sign bit (the MSB in two's complement) is shifted in on the
         * left, thus preserving the sign of the operand.*/

        int x00F7 = 0x00F7; //1111 0111
        System.out.println("x00F7: " + x00F7);
        System.out.println("x00F7 << 0 =  " + (x00F7 << 0)); //247  = 0000 0000 1111 0111
        System.out.println("x00F7 << 1 =  " + (x00F7 << 1)); //494  = 0000 0001 1110 1110
        System.out.println("x00F7 << 3 =  " + (x00F7 << 3)); //1976 = 0000 0111 1011 1000
        System.out.println("x00F7 >> 0 =  " + (x00F7 >> 0)); //247  = 0000 0111 1011 1000
        System.out.println("x00F7 >> 1 =  " + (x00F7 >> 1)); //123  = 0000 0000 1111 0111
        System.out.println("x00F7 >> 3 =  " + (x00F7 >> 3)); //30   = 0000 0000 0001 1110
    }

}
