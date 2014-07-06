package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Flags {

    NONE(0x00),
    COMPRESSION(0x01),
    TRACING(0x02),
    BOTH(0x03),;

    public final byte value;

    Flags(int value) {
        this.value = (byte) value;
    }

    public static Flags valueOf(byte value) {
        for (Flags v : values()) {
            if (v.value == value) return v;
        }
        throw new RuntimeException("invalid value");
    }
}
