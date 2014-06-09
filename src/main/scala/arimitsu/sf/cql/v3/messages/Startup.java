package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.*;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 14/06/07.
 */
public class Startup implements Request {
    public final short streamId;
    public final Flags flags;
    public final Compression compression;

    public Startup(short streamId, Flags flags, Compression compression) {
        this.streamId = streamId;
        this.flags = flags;
        this.compression = compression;
    }

    @Override
    public Frame toFrame() {
        if (compression != null && !Compression.NONE.equals(compression)) {
            parameters.put(Compression.KEY, compression.name);
        }
        byte[] body = Notation.toStringMap(parameters);
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.STARTUP), body.length, ByteBuffer.wrap(body));
    }

    public final Map<String, String> parameters = new HashMap<String, String>() {{
        put("CQL_VERSION", "3.0.0");
    }};

}
