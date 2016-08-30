package o.quote.business;

import java.util.Random;

public class QuoteGenerator {
    private final Random rand = new Random(System.currentTimeMillis());
    private String[] quotes = null;

    public QuoteGenerator(String lang) {
        if (lang.equalsIgnoreCase("es")) {
            quotes = new String[]{"No importa que la rama cruja, porque el ave sabe lo que son sus alas"
                    , "Dame una palanca y moveré el mundo"
                    , "La sabiduría nos llega cuando ya no nos sirve de nada"
                    , "Solo un idiota puede ser totalmente feliz"
                    , "El que busca la verdad corre el riesgo de encontrarla"
                    };
        } else if (lang.equalsIgnoreCase("en")) {
            quotes = new String[]{"The only source of knowledge is experience"
                    , "Only perfect practice makes perfect"
                    , "Experience is simply the name we give our mistakes"
                    , "Management is doing things right. Leadership is doing the right things"
                    };
        } else {
            quotes = new String[]{"Aut viam inveniam aut faciam"};
        }
    }

    public String generateQuote() {
        return quotes[rand.nextInt(quotes.length)];
    }
}
