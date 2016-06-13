package o.patterns.demo;

import java.util.Random;

/**
 * The State Pattern allows an object to alter its behavior
 * when its internal state changes. The object will appear to
 * change its class.
 */
public class StatePatternDemo {

	public static void main(String[] args) {
		CrapsMachine machine = new CrapsMachine();

		while(machine.getState() !=	machine.getWinnerState() 
				&& machine.getState() != machine.getLoserState()) {
			machine.throwDice();
		}
		machine.status();
	}

}

class CrapsMachine {
	int newPoint;
	int oldPoint;
	State current;
	State initial;
	State winner;
	State loser;
	State playing;
	static Random rand;

	CrapsMachine() {
		rand = new Random(System.currentTimeMillis());
		this.newPoint = 0;
		this.oldPoint = 0;
		this.initial = new InitialState(this);
		this.winner = new WinnerState(this);
		this.loser = new LoserState(this);
		this.playing = new PlayingState(this);
		//set the current state
		current = this.initial;
	}

	public void setState(State state) {
		this.current = state;
	}
	
	public State getState() {
		return current;
	}
	
	public State getInitialState() {
		return this.initial;
	}

	public State getWinnerState() {
		return this.winner;
	}
	
	public State getLoserState() {
		return this.loser;
	}
	
	public State getPlayingState() {
		return this.playing;
	}
	
	public int getOldPoint() {
		return this.oldPoint;
	}
	
	public int getNewPoint() {
		return this.newPoint;
	}
	
	public void throwDice() {
		current.describe();
		oldPoint = newPoint;
		newPoint = getDiceValue() + getDiceValue();
		System.out.println("point " + newPoint);
		current.evaluate();
	}
	public void status() {
		current.describe();
	}
	
	private int getDiceValue() {
		int value = 1 + rand.nextInt(6);
		return value;
	}
}

interface State {
	public void evaluate();
	public void describe();
}

class InitialState implements State {
	CrapsMachine machine;
	InitialState(CrapsMachine machine) {
		this.machine = machine;
	}

	@Override
	public void evaluate() {
		int newPnt = machine.getNewPoint();
		if(newPnt == 7 || newPnt == 11) {
			machine.setState(machine.getWinnerState());
		} else if (newPnt == 2 || newPnt == 3 || newPnt == 12) {
			machine.setState(machine.getLoserState());
		} else {
			machine.setState(machine.getPlayingState());
			machine.throwDice();
		}
	}
	
	@Override
	public void describe() {
		System.out.print("Throwing the dice... ");
	}
}

class WinnerState implements State {
	CrapsMachine machine;
	WinnerState(CrapsMachine machine) {
		this.machine = machine;
	}

	@Override
	public void evaluate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void describe() {
		System.out.print("YOU ARE THE WINNER !!! ");
	}
}

class LoserState implements State {
	CrapsMachine machine;
	LoserState(CrapsMachine machine) {
		this.machine = machine;
	}

	@Override
	public void evaluate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void describe() {
		System.out.print("YOU LOSE... bye, bye");
	}
}

class PlayingState implements State {
	CrapsMachine machine;
	PlayingState(CrapsMachine machine) {
		this.machine = machine;
	}

	@Override
	public void evaluate() {
		int newPnt = machine.getNewPoint();
		int oldPnt = machine.getOldPoint();
		if(newPnt == 11 || newPnt == oldPnt) {
			machine.setState(machine.getWinnerState());
		} else if (newPnt == 7) {
			machine.setState(machine.getLoserState());
		} else {
			machine.throwDice();
		}
	}
	
	@Override
	public void describe() {
		System.out.print("throwing again... ");
	}
}