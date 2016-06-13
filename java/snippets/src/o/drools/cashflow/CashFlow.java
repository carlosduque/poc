package o.drools.cashflow;

import java.util.Date;

public class CashFlow {
    private final Date date;
    private final double amount;
    private final String type;
    private final long accountNo;
    /**
     * @return the accountNo
     */
    public final long getAccountNo() {
        return accountNo;
    }
    /**
     * @param pDate Date of the movement
     * @param pAmount amount
     * @param pType type of movement (CREDIT, DEBIT)
     * @param pAccountNo account number
     */
    public CashFlow(final Date pDate, final double pAmount, final String pType, final long pAccountNo) {
        super();
        this.date = pDate;
        this.amount = pAmount;
        this.type = pType;
        this.accountNo = pAccountNo;
    }
    /**
     * @return the date
     */
    public final Date getDate() {
        return date;
    }

    /**
     * @return the amount
     */
    public final double getAmount() {
        return amount;
    }
    /**
     * @return the type
     */
    public final String getType() {
        return type;
    }

}
