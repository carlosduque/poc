package o.io;
public class ByteArray {
    public static void main(String[] args) {
        byte b = 127;
        char c = 'X';

        byte[] byteArray = {64,'w','W','\n','@','M','M','M','B'};
        String str = "Una luz, un viento fuerte cual agil torrente que se haga presente.";
        String byte2Str;
        byte[] msg;

        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("byteArray.toString():" + byteArray.toString());
        for (int i = 0; i < byteArray.length; i++) {
            System.out.println("byteArray[" + i + "]: " + byteArray[i] + "   (char)" + (char)byteArray[i]);
        }

        msg = new byte[255];
        msg = str.getBytes();
        System.out.println(byteArray.length);

        byte2Str = new String(byteArray);

        System.out.println("byte2Str: " + byte2Str);
    }

}
