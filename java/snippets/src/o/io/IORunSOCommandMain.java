package o.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IORunSOCommandMain {

    public static void main(String[] args) {
        ProcessBuilder pb = null;
        Process p = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            pb = new ProcessBuilder("c:/apps/cygwin/bin/du","-sk");
            p = pb.start();
            isr = new InputStreamReader(p.getInputStream());
            br = new BufferedReader(isr);
            String str = "";
            int counter = 0;
            br.mark(1);
            System.out.println("bra: " + br);
            while((str = br.readLine()) != null) {
                System.out.println("counter: " + counter + "| " + str);
                if(counter == 3)
                    br.reset();
                ++counter;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
