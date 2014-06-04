package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Consistency {
    ANY((short) 0x0000),
    ONE((short) 0x0001),
    TWO((short) 0x0002),
    THREE((short) 0x0003),
    QUORUM((short) 0x0004),
    ALL((short) 0x0005),
    LOCAL_QUORUM((short) 0x0006),
    EACH_QUORUM((short) 0x0007),
    SERIAL((short) 0x0008),
    LOCAL_SERIAL((short) 0x0009),
    LOCAL_ONE((short) 0x000A),;
    public final short value;

    Consistency(short value) {
        this.value = value;
    }

    public static Consistency valueOf(short value) {
        for (Consistency v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        throw new RuntimeException("invalid value");
    }
}
