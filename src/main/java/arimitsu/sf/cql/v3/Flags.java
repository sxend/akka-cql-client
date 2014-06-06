package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Flags {
    NONE((byte) 0x00),
    COMPRESSION((byte) 0x01), // Compression flag.
    TRACING((byte) 0x02); // Tracing flag.
    public final byte value;

    Flags(byte value) {
        this.value = value;
    }

    public static boolean isCompression(byte value) {
        return (COMPRESSION.value & value) > 1;
    }

    public static boolean isTracing(byte value) {
        return (TRACING.value & value) > 1;
    }
}
