package o.drools.cashflow;

import java.util.Date;

public class AccountPeriod {
    private final Date start;
    private final Date end;

    /**
     * @param start
     * @param end
     */
    public AccountPeriod(final Date pStart, final Date pEnd) {
        super();
        this.start = pStart;
        this.end = pEnd;
    }

    /**
     * @return the start
     */
    public final Date getStart() {
        return start;
    }

    /**
     * @return the end
     */
    public final Date getEnd() {
        return end;
    }

}
