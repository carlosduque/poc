package o.generics;

public class GenericsMain {

    public static void main(String[] args) {
        MyGeneric<String> myGenericString = new MyGeneric<String>();
        myGenericString.set("Go Red Sox !!!");

        MyGeneric<Integer> myGenericInteger = new MyGeneric<Integer>();
        myGenericInteger.set(29);

        MyGeneric<Box> myGenericBox = new MyGeneric<Box>();
        myGenericBox.set(new Box());

        System.out.println(myGenericString.get());
        System.out.println(myGenericInteger.get());
        System.out.println(myGenericBox.get());

        MySubGeneric<Box> mySubBox = new MySubGeneric<Box>();
        mySubBox.put(new Box());
        System.out.println(mySubBox.def());

        MySubGeneric<Integer> mySubInt = new MySubGeneric<Integer>();
        mySubInt.put(new Integer(1));
        System.out.println(mySubInt.def());

    }

}
