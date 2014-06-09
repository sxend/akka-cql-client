package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Version {
    REQUEST(0x3),
    RESPONSE(0x83),;
    public final byte number;

    Version(int number) {
        this.number = (byte) number;
    }

    public static Version valueOf(byte number) {
        for (Version v : values()) {
            if (v.number == number) return v;
        }
        throw new RuntimeException("invalid number");
    }
}