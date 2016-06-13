public class CharString {
  public static void main(String[] args) {
    // chars are 16bit unsigned integers under the hood
    char a = 'a';
    char b = '@';
    char letterN = '\u004E';  // The letter 'N'
    System.out.println("a = " + a + " b = " + b + " letterN = " + letterN);

    char ha = 0x892;    // hexadecimal literal
    char c = 982;       // int literal
    char d = (char) 70000;  // the cast is required; 70000 is out of char range
    System.out.println("ha = " + ha + " c = " + c + " d = " + d);

    char e = (char) -98;  //ridiculous, but legal
    //char f = -29;  // possible loss of precision; needs a cast
    //char g = 7000;  // possible loss of precision; needs a cast
    
	System.out.println("e = " + e);

    char h = '\"';
    char i = '\n';
	System.out.println("h = " + h + " i = " + i);
    
  }
}