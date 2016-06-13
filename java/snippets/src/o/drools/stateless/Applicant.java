package o.drools.stateless;

/**
 * A driver's license applicant.
 * @author carlos.duque
 *
 */
public class Applicant {
    /**applicant's name.*/
    private String name;
    /**applicant's age.*/
    private int age;

    /**
     * Constructor.
     * @param pName of the applicant
     * @param pAge of the applicant
     */
    public Applicant(final String pName, final int pAge) {
        super();
        this.name = pName;
        this.age = pAge;
    }
    /**
     * Name accesor.
     * @return the applicant's name
     */
    public String getName() {
    	return name;
	}
    /**
     * Name mutator.
     */
	public void setName(final String pName) {
		this.name = pName;
	}
    /**
     * Age accesor.
     * @return the applicant's age
     */
	public int getAge() {
		return age;
	}
    /**
     * Age mutator.
     */
	public void setAge(final int pAge) {
		this.age = pAge;
	}

}
