package o.drools.cashflow;

public class Account {

    private final long accountNo;
    private double balance;

    /**
     * @param accountNo
     * @param balance
     */
    public Account(final long pAccountNo) {
        super();
        this.accountNo = pAccountNo;
        this.balance = 0;
    }
    /**
     * @return the accountNo
     */
    public final long getAccountNo() {
        return accountNo;
    }

    /**
     * @return the balance
     */
    public final double getBalance() {
        return balance;
    }
    /**
     * @param pBalance the balance to set
     */
    public final void setBalance(final double pBalance) {
        this.balance = pBalance;
    }
}
