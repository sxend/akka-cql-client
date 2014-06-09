package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/08.
 */
public enum Compression {

    NONE(""),
    LZ4("lz4"),
    SNAPPY("snappy"),;
    public final String name;
    public static final String KEY ="COMPRESSION";

    Compression(String name) {
        this.name = name;
    }

    public Compression valueOf(byte[] name) {
        String strName = new String(name);
        for (Compression c : values()) {
            if (c.name.equals(strName)) return c;
        }
        return NONE;
    }
}

