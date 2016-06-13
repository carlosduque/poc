package o.drools.stateful;

/**
 * The room.
 * @author carlos.duque
 *
 */
public class Room {
    /** the name of the room.*/
    private String name;

    /**
     * @param pName
     */
    public Room(String pName) {
        super();
        this.name = pName;
    }

    /**
     * @return the name
     */
    public final String getName() {
		return name;
	}

    public String toString() {
        return this.name;
    }
}
