package o.io.jmx;

public interface FileMemConsumptionMBean {
    public void printStats();
    public void readFileBufferedReader(String filename);
    public void readFileInputStream(String filename);
    public void release();
}
