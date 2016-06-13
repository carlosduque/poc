package o.drools.stateless;

import java.util.Date;

/**
 * A driver's license application.
 * @author carlos.duque
 *
 */
public class Application {
    /** the date of the application.*/
    private Date dateApplied;
    /** is the application valid.*/
    private boolean valid;

    /**
     * @param pDateApplied the date of the application
     * @param pValid flag to represent if the application is valid
     */
    public Application(final Date pDateApplied, final boolean pValid) {
        super();
        this.dateApplied = pDateApplied;
        this.valid = pValid;
    }

    /**
     * @return the dateApplied
     */
    public final Date getDateApplied() {
        return dateApplied;
    }
    /**
     * @param pDateApplied the dateApplied to set
     */
    public final void setDateApplied(final Date pDateApplied) {
        this.dateApplied = pDateApplied;
    }

    /**
     * @return the valid
     */
    public final boolean isValid() {
        return valid;
    }

    /**
     * @param pValid the valid to set
     */
    public final void setValid(final boolean pValid) {
        this.valid = pValid;
    }
}
