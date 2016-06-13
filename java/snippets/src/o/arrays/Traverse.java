/**
 *
 */
package o.arrays;

import java.util.Arrays;

public class Traverse {

    public static int findStatusCode(int statusCd) {
        int mappingCd = 400;
        int[] codes = new int[]{200, 203, 250, 290, 291, 292, 295, 599,100, 103, 105};
        for (int i=0; i < codes.length; ++i) {
            System.out.printf("codes[%d]: %d | statusCd: %d %n", i, codes[i], statusCd);
            if (codes[i] == statusCd) {
                System.out.printf("setting mappingCd = 100 %n");
                mappingCd = 100;
            }
        }

        return mappingCd;
    }

    public static int  findStatusCodeFaster(int statusCode) {
        int[] codes = new int[] {200, 203, 250, 290, 291, 292, 295, 599,100, 103, 105};
        Arrays.sort(codes);
        int idx = Arrays.binarySearch(codes, statusCode);
        if (idx > 0)
            return 100;
        return 400;
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);

        int[] a = new int[size];
        int accesses = 0;

        System.out.println("original array:");
        for (int i = 0; i < size; i++) {
          a[i] = i;
          System.out.printf("a[%d]=%d ", i, a[i]);
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
              //System.out.printf("a[%d]:%d | a[%d]:%d | a[%d]:%d %n", i, a[i], j, a[j], k, a[k]);
              accesses++;
            }
            //System.out.println();
          }
          //System.out.println();
        }
        System.out.println("total array accesses: " + accesses);

        accesses = 0;
        for (int i = 0; i < size; i++) {
          for (int j = i+1; j < size; j++) {
            for (int k = j+1; k < size; k++) {
              System.out.printf("a[%d]:%d | a[%d]:%d | a[%d]:%d %n", i, a[i], j, a[j], k, a[k]);
              accesses++;
            }
            System.out.println();
          }
          System.out.println();
        }
        System.out.println("total array accesses: " + accesses);

        long start = System.currentTimeMillis();
        int codeFound = findStatusCode(1291);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("findStatusCode, mapped to: " + codeFound + ", took " + elapsed + "ms");
        start = System.currentTimeMillis();
        codeFound = findStatusCodeFaster(1291);
        elapsed = System.currentTimeMillis() - start;
        System.out.println("findStatusCodeFaster, mapped to: " + codeFound + ", took " + elapsed + "ms");

    }

}
