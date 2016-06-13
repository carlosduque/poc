package o.drools.stateful;

public class Fire {

    private Room room;

    /**
     * @param room
     */
    public Fire(Room room) {
        super();
        this.room = room;
    }

    /**
     * @return the room
     */
    public final Room getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public final void setRoom(Room pRoom) {
        this.room = pRoom;
    }
    
    public String toString() {
        return "Fire on " + (this.room != null ? this.room.getName() : " null");
    }
}
