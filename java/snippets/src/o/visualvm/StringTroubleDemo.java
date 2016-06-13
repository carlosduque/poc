package o.visualvm;

interface StringDemo {
   void add(long reps);
}

class WithString implements StringDemo {
   private String str = "";

   public void add(long reps) {
      try {
         for (int i = 0; i <= reps; i++) {
            str += "#" + i + " ";
            Thread.sleep(1);
         }
      } catch (Exception e) {
         System.err.printf("exception: ", e);
      }
   }

   public String toString() {
      return str;
   }
}

class WithStringBuffer implements StringDemo {
   private StringBuffer sb = new StringBuffer("");

   public void add(long reps) {
      try {
         for (int i = 0; i <= reps; i++) {
            sb.append("#").append(i + " ");
            Thread.sleep(1);
         }
      } catch (Exception e) {
         System.err.printf("exception: ", e);
      }
   }

   public String toString() {
      return sb.toString();
   }
}

public class StringTroubleDemo {
   private static long REPETITIONS = 100000L;

   public static void main(String[] args) {
      StringDemo sd = null;

      if (args[0].equalsIgnoreCase("s")) {
         sd = new WithString();
      } else {
         sd = new WithStringBuffer();
      }

      sd.add(REPETITIONS);
      System.out.printf("%s%n", sd.toString());
   }

 }