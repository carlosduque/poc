/**
 *
 */
package o.enums;

import java.util.Arrays;

/**
 * @author a07942a
 * @version May 22, 2014
 *
 */
public class Card {

        public enum Rank { DEUCE, THREE, FOUR, FIVE, SIX,
            SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }

        public enum Suit {
            CLUBS,
            DIAMONDS,
            HEARTS,
            SPADES;

            public String toString() {
                return super.toString().toLowerCase();
            }
        }

        public static void main(String[] args) {
            System.out.println(Suit.SPADES);
            System.out.println(Suit.valueOf(Suit.class, "DIAMONDS"));
            System.out.println(Suit.valueOf("SPADES"));
            System.out.println(Arrays.asList(Suit.values()));

        }
}
