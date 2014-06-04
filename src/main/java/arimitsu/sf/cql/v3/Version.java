package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Version {
    REQUEST((byte) 0x03),    // Request frame for this protocol version
    RESPONSE((byte) 0x83)    // Response frame for this protocol version
    ;
    public final byte value;

    Version(byte value) {
        this.value = value;
    }

    public static Version valueOf(byte value) {
        for (Version v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        throw new RuntimeException("invalid value");
    }
}
