package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Opcode {
    ERROR(0x00),
    STARTUP(0x01),
    OPTIONS(0x05),
    QUERY(0x07),
    AUTH_RESPONSE(0x0F),
    PREPARE(0x09),
    EXECUTE(0x0A),
    BATCH(0x0D),
    REGISTER(0x0B),
    READY(0x02),
    AUTHENTICATE(0x03),
    RESULT(0x08),
    SUPPORTED(0x06),
    AUTH_CHALLENGE(0x0E),
    AUTH_SUCCESS(0x10),
    EVENT(0x0C),;
    public final byte number;

    Opcode(int number) {
        this.number = (byte) number;
    }

    public static Opcode valueOf(byte number) {
        for (Opcode v : values()) {
            if (v.number == number) return v;
        }
        throw new RuntimeException("invalid number");
    }
}
