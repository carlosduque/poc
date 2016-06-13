class Moo {
  public void useAZoo() {
    Zoo z = new Zoo();
    // If preceding line compiles Moo has access
    // to the Zoo class
    // But... does it hane access to the coolMethod()?
    System.out.println("A Zoo says, " + z.coolMethod());
    // The preceding line works because Moo can access the
    // public method
  }
  public static void main(String[] args) {
    Moo moo = new Moo();
    moo.useAZoo();
  }
}

