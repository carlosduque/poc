package o.patterns.strategy;

interface IStrategy {
    public void method();
}

class ConcreteStrategyA implements IStrategy {
    public void method() {
        System.out.println(this + ".method()");
    }
}

class ConcreteStrategyB implements IStrategy {
    public void method() {
        System.out.println(this + ".method()");
    }
}

class Context {
    private IStrategy strategy;

    Context() {
        System.out.println("Context()");
    }
    
    public void setStrategy(IStrategy strategy) {
        System.out.println(this + ".setStrategy("+ strategy + ")");
        this.strategy = strategy;
    }
    
    public void method() {
        System.out.println(this + ".method()");
        this.strategy.method();
    }
}

public class StrategyPattern {
    public static void main(String[] args) {
        System.out.println("*** StrategyPattern ***");
        Context context = new Context();
        context.setStrategy(new ConcreteStrategyA());
        context.method();
        context.setStrategy(new ConcreteStrategyB());
        context.method();
    }
}
