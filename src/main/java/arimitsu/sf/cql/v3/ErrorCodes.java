package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum ErrorCodes {
    SERVER_ERROR(0x0000),
    PROTOCOL_ERROR(0x000A),
    BAD_CREDENTIALS(0x0100),
    UNAVAILABLE_EXCEPTION(0x1000),
    OVERLOADED(0x1001),
    IS_BOOTSTRAPPING(0x1002),
    TRUNCATE_ERROR(0x1003),
    WRITE_TIMEOUT(0x1100),
    READ_TIMEOUT(0x1200),
    SYNTAX_ERROR(0x2000),
    UNAUTHORIZED(0x2100),
    INVALID(0x2200),
    CONFIG_ERROR(0x2300),
    ALREADY_EXISTS(0x2400),
    UNPREPARED(0x2500),;

    public final int value;

    ErrorCodes(int value) {
        this.value = value;
    }

    public static ErrorCodes valueOf(int value) {
        for (ErrorCodes e : values()) {
            if (e.value == value) {
                return e;
            }
        }
        throw new RuntimeException("invalid value");
    }
}
