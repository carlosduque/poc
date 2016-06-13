public class Numbers {
  public static void main(String[] args) {
    int six = 06;     //Equal to decimal 6
    int seven = 07;   //Equal to decimal 7
    int eight = 010;  //Equal to decimal 8
    int nine = 011;   //Equal to decimal 9
	System.out.println("Octal 010 = " + eight);
	
	int x = 0X0001;
	int y = 0x7fffffff;
	int z = 0xDeadCafe;
	System.out.println("x = " + x + " y = " + y + " z = " + z);
	
	long jo = 110599L;
	long so = 0xFFFFl;  // Note the lowercase 'l'
	
	//float ff = 23.467890;   //Compiler error, possible loss of precision
	                      // floating-point literals are defined as 'double' (64bits) by default
						  // but the float is 32bits
	float ff = 23.467890F;
    //byte a = 128; // byte can only hold up to 127 so a loss of precision is in place
    byte a = (byte) 128; 
    System.out.println("byte a = (byte) 128 results in: " + a);
  }
}
