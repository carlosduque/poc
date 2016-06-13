import java.lang.Integer;

public class N3 {
    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        for (int i = 0; i < max; i++)
            for (int j = i + 1; j < max; j++)
                for (int k = j + 1; k < max; k++)
                    StdOut.println("i=" + i + ", j=" + j + ", k=" + k);
    }
}
