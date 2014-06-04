package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Opcode {


    ERROR((byte) 0x00),
    STARTUP((byte) 0x01),
    READY((byte) 0x02),
    AUTHENTICATE((byte) 0x03),
    OPTIONS((byte) 0x05),
    SUPPORTED((byte) 0x06),
    QUERY((byte) 0x07),
    RESULT((byte) 0x08),
    PREPARE((byte) 0x09),
    EXECUTE((byte) 0x0A),
    REGISTER((byte) 0x0B),
    EVENT((byte) 0x0C),
    BATCH((byte) 0x0D),
    AUTH_CHALLENGE((byte) 0x0E),
    AUTH_RESPONSE((byte) 0x0F),
    AUTH_SUCCESS((byte) 0x10),;

    public final byte value;

    Opcode(byte value) {
        this.value = value;

    }

    public static Opcode valueOf(byte value) {
        for (Opcode op : values()) {
            if (op.value == value) {
                return op;
            }
        }
        throw new RuntimeException("invalid value");
    }

}
