package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum OpcodeType {
    REQUEST(
            new Opcode[]{Opcode.STARTUP,
                    Opcode.AUTH_RESPONSE,
                    Opcode.OPTIONS,
                    Opcode.QUERY,
                    Opcode.PREPARE,
                    Opcode.EXECUTE,
                    Opcode.BATCH,
                    Opcode.REGISTER}
    ),
    RESPONSE(
            new Opcode[]{
                    Opcode.ERROR,
                    Opcode.READY,
                    Opcode.AUTHENTICATE,
                    Opcode.SUPPORTED,
                    Opcode.RESULT,
                    Opcode.EVENT,
                    Opcode.AUTH_CHALLENGE,
                    Opcode.AUTH_SUCCESS
            }
    );
    public final Opcode[] opcodes;

    OpcodeType(Opcode[] opcodes) {
        this.opcodes = opcodes;
    }
}
