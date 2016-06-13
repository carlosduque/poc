package o.threads;

public class WaNot{
   int i=0;
   public static void main(String argv[]){
      WaNot w = new WaNot();
      w.amethod();
   }

   public void amethod(){
      while(true){
         try{ 
            wait();
         } catch (InterruptedException e) {
            System.out.println(e);
         }
         i++;
      } //End of while

   }//End of amethod
} //End of class
