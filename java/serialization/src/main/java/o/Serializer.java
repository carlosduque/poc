package o;

import java.io.File;
import o.beans.Author;

public interface Serializer {
    void serialize(Author author, File output);
    Author deserialize(File input);
}
