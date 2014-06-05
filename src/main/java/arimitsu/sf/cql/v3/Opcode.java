package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Opcode {
    ERROR((byte) 0x00),
    //req
    STARTUP((byte) 0x01),
    QUERY((byte) 0x07),
    AUTH_RESPONSE((byte) 0x0F),
    PREPARE((byte) 0x09),
    EXECUTE((byte) 0x0A),
    BATCH((byte) 0x0D),
    REGISTER((byte) 0x0B),
    //res
    READY((byte) 0x02),
    RESULT((byte) 0x08),
    AUTHENTICATE((byte) 0x03),
    OPTIONS((byte) 0x05),
    SUPPORTED((byte) 0x06),
    AUTH_CHALLENGE((byte) 0x0E),
    AUTH_SUCCESS((byte) 0x10),
    // push
    EVENT((byte) 0x0C),
    ;
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

    public static class REQ_OP {
        public static final Opcode STARTUP = Opcode.STARTUP;
        public static final Opcode QUERY = Opcode.QUERY;
        public static final Opcode AUTH_RESPONSE = Opcode.AUTH_RESPONSE;
        public static final Opcode PREPARE = Opcode.PREPARE;
        public static final Opcode EXECUTE = Opcode.EXECUTE;
        public static final Opcode BATCH = Opcode.BATCH;
        public static final Opcode REGISTER = Opcode.REGISTER;

    }

    public static class RES_OP {
        public static final Opcode ERROR = Opcode.ERROR;
        public static final Opcode READY = Opcode.READY;
        public static final Opcode AUTHENTICATE = Opcode.AUTHENTICATE;
        public static final Opcode OPTIONS = Opcode.OPTIONS;
        public static final Opcode SUPPORTED = Opcode.SUPPORTED;
        public static final Opcode RESULT = Opcode.RESULT;
        public static final Opcode EVENT = Opcode.EVENT;
        public static final Opcode AUTH_CHALLENGE = Opcode.AUTH_CHALLENGE;
        public static final Opcode AUTH_SUCCESS = Opcode.AUTH_SUCCESS;
    }
}
