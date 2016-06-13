package o.io;

import java.io.Console;
import java.io.File;

/**
 * @author a07942a
 * @version Jun 19, 2014
 *
 */
public class FileManipulation {
    private static final String SPACE = " ";

    File file = null;
    public FileManipulation(String f) {
        file = new File(f);
    }

    public String stats() {
        StringBuilder sb = new StringBuilder();
        sb.append("length:" + file.length()).append(SPACE)
        .append("free space: " + file.getFreeSpace()).append(SPACE)
        .append("total space: " + file.getTotalSpace()).append(SPACE)
        .append("usable space: " + file.getUsableSpace());
        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String filename = args[0];
        FileManipulation sut = new FileManipulation(filename);
        System.out.println("stats: [" + sut.stats() + "]");

    }

}
