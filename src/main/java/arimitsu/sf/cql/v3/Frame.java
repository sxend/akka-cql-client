package arimitsu.sf.cql.v3;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/04.
 */
public class Frame {
    public final Header header;
    public final int length;
    public final byte[] body;

    public Frame(ByteBuffer byteBuffer) {
        byte version = byteBuffer.get();
        byte flags = byteBuffer.get();
//        int streamId = byteBuffer.getShort();
//        streamId = (streamId << 8) + (byteBuffer.get() & 0xff);
//        if(streamId < 0 || streamId > 32768)
        short streamId = byteBuffer.getShort();
        byte opcode = byteBuffer.get();
        int length = byteBuffer.getInt();
//        length = (length << 8) + (0xff & byteBuffer.get());
//        length = (length << 8) + (0xff & byteBuffer.get());
//        length = (length << 8) + (0xff & byteBuffer.get());
        byte[] body = new byte[length];
        byteBuffer.get(body);
        this.header = new Header(Version.valueOf(version), flags, streamId, Opcode.valueOf(opcode));
        this.length = length;
        this.body = body;
    }

    public Frame(Version version, byte flags, short streamId, Opcode opcode, byte[] body) {
        this.header = new Header(version, flags, streamId, opcode);
        this.length = body.length;
        this.body = body;
    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + this.length);
        byte[] headerBytes = new byte[9];
        headerBytes[0] = this.header.version.value;
        headerBytes[1] = this.header.flags;
        headerBytes[2] = (byte) (0xff & (this.header.streamId >>> 8));
        headerBytes[3] = (byte) (0xff & (this.header.streamId));
        headerBytes[4] = this.header.opcode.value;
        headerBytes[5] = (byte) (0xff & (this.length >>> 24));
        headerBytes[6] = (byte) (0xff & (this.length >>> 16));
        headerBytes[7] = (byte) (0xff & (this.length >>> 8));
        headerBytes[8] = (byte) (0xff & (this.length));
        byteBuffer.put(headerBytes);
        byteBuffer.put(this.body);
        return byteBuffer;
    }


}


