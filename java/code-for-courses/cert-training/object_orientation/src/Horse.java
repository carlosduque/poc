public class Horse extends Animal {
  private Halter myHalter = new Halter();
  public void tie(Rope r) {
    myHalter.tie(r);
  }
  public static void main(String[] args) {
    Horse horse = new Horse();
    horse.tie(new Rope());
  }
}
