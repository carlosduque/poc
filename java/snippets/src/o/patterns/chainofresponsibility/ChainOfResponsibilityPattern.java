package o.patterns.chainofresponsibility;

interface Handler {
    void next(Handler successor);
    void handle(Request request);
}
class Request {
    int condition = 0;
    Request (int conditionValue) {
        condition = conditionValue;
    }
    int getCondition() {
        return condition;
    }
}
class ConcreteHandlerOne implements Handler {
    ConcreteHandlerOne() {
        System.out.println("ConcreteHandlerOne()");
    }

    Handler next;
    public void next(Handler successor) {
        System.out.println(this + ".next(" + successor + ")");
        next = successor;
    }
    public void handle(Request request) {
        System.out.println(this + ".handle(" + request.getCondition() + ")");
        if (request.getCondition() == 1) {
            System.out.println(this + " handled the request !!");
        } else {
            next.handle(request);
        }
    }
}

class ConcreteHandlerTwo implements Handler {
    ConcreteHandlerTwo() {
        System.out.println("ConcreteHandlerTwo()");
    }
    Handler next;
    public void next(Handler successor) {
        System.out.println(this + ".next(" + successor + ")");
        next = successor;
    }
    public void handle(Request request) {
        System.out.println(this + ".handle(" + request.getCondition() + ")");
        if (request.getCondition() == 2) {
            System.out.println(this + " handled the request !!");
        } else {
            next.handle(request);
        }
    }
}

class ConcreteHandlerThree implements Handler {
    ConcreteHandlerThree() {
        System.out.println("ConcreteHandlerThree()");
    }
    Handler next;
    public void next(Handler successor) {
        System.out.println(this + ".next("+ successor +")");
        next = successor;
    }
    public void handle(Request request) {
        System.out.println(this + ".handle(" + request.getCondition() + ")");
        if (request.getCondition() >= 3) {
            System.out.println(this + " handled the request !!");
        } else {
            next.handle(request);
        }
    }
}

public class ChainOfResponsibilityPattern {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("*** ChainOfResponsibilityPattern ***");
        ConcreteHandlerOne h1 = new ConcreteHandlerOne();
        ConcreteHandlerTwo h2 = new ConcreteHandlerTwo();
        ConcreteHandlerThree h3 = new ConcreteHandlerThree();
        h1.next(h2);
        h2.next(h3);
        h3.next(null);

        Request req = new Request(3);
        h1.handle(req);
        req = new Request(1);
        h1.handle(req);
        req = new Request(2);
        h1.handle(req);

    }

}
