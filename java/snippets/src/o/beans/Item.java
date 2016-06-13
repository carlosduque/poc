package o.beans;

public class Item {
	  private static int counter = 0;
	  private int goal;
	  private int steps;
	  private boolean finished;

	  public Item() {
	    this(10);
	  }

	  public Item(final int newGoal) {
	    counter = counter + 1;
	    steps = 0;
	    finished = false;
	    goal = newGoal;
	  }

	  public int getCounter() {
	    return Item.counter;
	  }

	  public void move(final int newMove) {
	    steps = steps + newMove;
	    finished = steps >= goal;
	  }

	  public String toString() {
	    String str = "finished:" + finished + " goal:" + goal + " steps given:" + steps;
	    return str;
	  }

}
