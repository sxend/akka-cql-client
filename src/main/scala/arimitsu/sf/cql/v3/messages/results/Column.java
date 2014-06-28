package arimitsu.sf.cql.v3.messages.results;

/**
 * Created by sxend on 14/06/28.
 */
public class Column {
    public final String name;
    public final Object value;

    public Column(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
