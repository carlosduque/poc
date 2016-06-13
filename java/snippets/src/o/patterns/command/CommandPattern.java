package o.patterns.command;

class Receiver {
    Receiver() {
        System.out.println("Receiver()");
    }
    public void actionOne() {
        System.out.println(this + ".actionOne()");
    }
    public void actionTwo() {
        System.out.println(this + ".actionTwo()");
    }
}

interface Command {
    public void execute();
}

class Invoker {
    Command command;
    Invoker() {
        System.out.println("Invoker()");
    }
    public void setCommand(Command cmd) {
        System.out.println(this + ".setCommand(" + cmd + ")");
        command = cmd;
    }
    public void invoke() {
        System.out.println(this + ".invoke()");
        command.execute();
    }
}

class ConcreteCommandA implements Command {
    private Receiver receiver;
    ConcreteCommandA(Receiver rcvr) {
        System.out.println("ConcreteCommandA(" + rcvr + ")");
        receiver = rcvr;
    }
    public void execute() {
        System.out.println(this + ".execute()");
        receiver.actionOne();
    }
}

class ConcreteCommandB implements Command {
    private Receiver receiver;
    ConcreteCommandB(Receiver rcvr) {
        System.out.println("ConcreteCommandB(" + rcvr + ")");
        receiver = rcvr;
    }
    public void execute() {
        System.out.println(this + ".execute()");
        receiver.actionTwo();
    }
}

public class CommandPattern {
    public static void main(String[] args) {
        System.out.println("*** CommandPattern ***");
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        invoker.setCommand(new ConcreteCommandA(receiver));
        invoker.invoke();
        invoker.setCommand(new ConcreteCommandB(receiver));
        invoker.invoke();
    }
}
