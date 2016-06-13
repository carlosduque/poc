package o.drools.stateful;

public class Sprinkler {
    /**
     * @param room
     */
    public Sprinkler(Room room) {
        super();
        this.room = room;
    }
    /** The room related to this sprinkler.*/
    private Room room;
    /** Flag to identify if the sprinkler is on.*/
    private boolean on;
    /**
     * @return the room
     */
    public final Room getRoom() {
        return room;
    }
    /**
     * @param pRoom the room to set
     */
    public final void setRoom(final Room pRoom) {
        this.room = pRoom;
    }
    /**
     * @return the on
     */
    public final boolean isOn() {
        return on;
    }
    /**
     * @param pOn the flag to set
     */
    public final void setOn(final boolean pOn) {
        this.on = pOn;
    }
    
}
