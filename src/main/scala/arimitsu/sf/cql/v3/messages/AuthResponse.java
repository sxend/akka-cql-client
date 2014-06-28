package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;

/**
 * Created by sxend on 14/06/07.
 */
public class AuthResponse implements Request {
    public final short streamId;
    public final Flags flags;
    public final byte[] token;

    public AuthResponse(short streamId, Flags flags, byte[] token) {
        this.streamId = streamId;
        this.flags = flags;
        this.token = token;
    }

    @Override
    public Frame toFrame() {
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.AUTH_RESPONSE), token);
    }
}
