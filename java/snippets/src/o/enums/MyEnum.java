package o.enums;

public class MyEnum {
    public enum Keys {
        INPUT_DIR("input"),
        OUTPUT_DIR("output");
        Keys(String dir) {
            dirname = dir;
        }
        private String dirname;
        public String dirname() {
            return dirname;
        }
    }

    public static void main(String[] args) {
        Keys mykeys = Keys.INPUT_DIR;
        System.out.println("mykeys.dirname(): " + mykeys.dirname());
        System.out.println("mykeys: " + mykeys);
        System.out.println("mykeys.valueOf(\"INPUT_DIR\"): " + mykeys.valueOf("INPUT_DIR"));
        System.out.println("mykeys.values() ");
        for (Keys v : Keys.values()) {
            System.out.println(" v: " + v);
        }
        System.out.println(" Keys.OUTPUT_DIR: " + Keys.OUTPUT_DIR);
        System.out.println(" Keys.INPUT_DIR: " + Keys.INPUT_DIR.toString());
    }

}