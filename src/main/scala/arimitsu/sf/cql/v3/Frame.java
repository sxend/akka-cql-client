package arimitsu.sf.cql.v3;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/04.
 */
public class Frame {
    public final Header header;
    public final int length;
    public final ByteBuffer body;

    public Frame(Header header, int length, ByteBuffer body) {
        this.header = header;
        this.length = length;
        this.body = body;
    }

    public Frame(ByteBuffer byteBuffer) {
        byte version = byteBuffer.get();
        byte flags = byteBuffer.get();
        short streamId = byteBuffer.getShort();
        byte opcode = byteBuffer.get();
        this.header = new Header(Version.valueOf(version), Flags.valueOf(flags), streamId, Opcode.valueOf(opcode));
        this.length = byteBuffer.getInt();
        this.body = byteBuffer;
    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + this.length);
        byte[] headerBytes = new byte[9];
        headerBytes[0] = header.version.number;
        headerBytes[1] = header.flags.value;
        headerBytes[2] = (byte) (this.header.streamId >>> 8);
        headerBytes[3] = (byte) (0xff & this.header.streamId);
        headerBytes[4] = header.opcode.number;
        headerBytes[5] = (byte) (this.length >>> 24);
        headerBytes[6] = (byte) (0xff & (this.length >>> 16));
        headerBytes[7] = (byte) (0xff & (this.length >>> 8));
        headerBytes[8] = (byte) (0xff & this.length);
        byteBuffer.put(headerBytes);
        if (this.body != null) {
            byteBuffer.put(this.body);
        }
        byteBuffer.flip();
        return byteBuffer;
    }
}
