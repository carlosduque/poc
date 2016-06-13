package o.patterns.demo;

/**
 * The State Pattern allows an object to alter its behavior
 * when its internal state changes. The object will appear to
 * change its class.
 */
public class StatePattern {

	public static void main(String[] args) {
		StateContext ctx = new StateContext();
		System.out.println("Current State: " + ctx.getCurrentStateName());
		System.out.print("Request:");
		ctx.request();
		System.out.println("Current State: " + ctx.getCurrentStateName());
		System.out.print("Request:");
		ctx.request();
		System.out.println("Current State: " + ctx.getCurrentStateName());
		System.out.print("Request:");
		ctx.request();
		System.out.println("Current State: " + ctx.getCurrentStateName());
		System.out.print("Request:");
		ctx.request();
	}

}

class StateContext {
	IState currentState;
	IState stateA;
	IState stateB;
	IState stateC;

	StateContext() {
		this.stateA = new ConcreteStateA(this);
		this.stateB = new ConcreteStateB(this);
		this.stateC = new ConcreteStateC(this);
		//set the current state
		currentState = new ConcreteStateA(this);
	}

	public void setState(IState state) {
		this.currentState = state;
	}
	
	public IState getCurrentState() {
		return currentState;
	}
	
	public String getCurrentStateName() {
		return currentState.getClass().getSimpleName();
	}
	
	public IState getStateA() {
		return this.stateA;
	}

	public IState getStateB() {
		return this.stateB;
	}
	
	public IState getStateC() {
		return this.stateC;
	}

	public void request() {
		currentState.handle();
	}
}

interface IState {
	public void handle();
}

class ConcreteStateA implements IState {
	StateContext context;
	ConcreteStateA(StateContext ctx) {
		context = ctx;
	}

	@Override
	public void handle() {
		System.out.println("I'm handling the behaviour for " + this.getClass().getSimpleName());
		context.setState(context.getStateB());
	}
}

class ConcreteStateB implements IState {
	StateContext context;
	ConcreteStateB(StateContext ctx) {
		context = ctx;
	}
	
	@Override
	public void handle() {
		System.out.println("I'm handling the behaviour for " + this.getClass().getSimpleName());
		context.setState(context.getStateC());
	}
}

class ConcreteStateC implements IState {
	StateContext context;
	ConcreteStateC(StateContext ctx) {
		context = ctx;
	}

	@Override
	public void handle() {
		System.out.println("I'm handling the behaviour for " + this.getClass().getSimpleName());
		context.setState(context.getStateA());
	}
	
}