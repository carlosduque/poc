package o.drools.sandbox;

public class Author {
    private final String name;
    private final String lastName;

    public Author(final String pName, final String pLastName) {
        super();
        this.name = pName;
        this.lastName = pLastName;
    }

    public final String getName() {
        return name;
    }

    public final String getLastName() {
        return lastName;
    }
}
