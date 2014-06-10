package arimitsu.sf.cql.v3;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/04.
 */
public class Frame {
    public final Header header;
    public final int length;
    public final byte[] body;

    public Frame(Header header, byte[] body) {
        this.header = header;
        this.length = body != null ? body.length : 0;
        this.body = body;
    }

    public Frame(ByteBuffer byteBuffer, Compression.Compressor compressor) {
        byte version = byteBuffer.get();
        byte flags = byteBuffer.get();
        short streamId = byteBuffer.getShort();
        byte opcode = byteBuffer.get();
        this.header = new Header(Version.valueOf(version), Flags.valueOf(flags), streamId, Opcode.valueOf(opcode));
        int length = byteBuffer.getInt();
        byte[] bytes = new byte[length];
        byteBuffer.get(bytes);
        switch (header.flags) {
            case COMPRESSION:
            case BOTH:
                bytes = compressor.decompress(bytes);
                length = bytes.length;
                break;
        }
        this.length = length;
        this.body = bytes;
    }

    public ByteBuffer toByteBuffer(Compression.Compressor compressor) {
        int length = this.length;
        byte[] bytes = this.body;
        if (this.body != null) {
            switch (header.flags) {
                case COMPRESSION:
                case BOTH:
                    bytes = compressor.compress(this.body);
                    length = bytes.length;
                    break;
            }
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + length);
        byte[] headerBytes = new byte[9];
        headerBytes[0] = header.version.number;
        headerBytes[1] = header.flags.value;
        headerBytes[2] = (byte) (this.header.streamId >>> 8);
        headerBytes[3] = (byte) (0xff & this.header.streamId);
        headerBytes[4] = header.opcode.number;
        headerBytes[5] = (byte) (length >>> 24);
        headerBytes[6] = (byte) (0xff & (length >>> 16));
        headerBytes[7] = (byte) (0xff & (length >>> 8));
        headerBytes[8] = (byte) (0xff & length);
        byteBuffer.put(headerBytes);
        if(bytes != null) byteBuffer.put(bytes);
        byteBuffer.flip();
        return byteBuffer;
    }
}
