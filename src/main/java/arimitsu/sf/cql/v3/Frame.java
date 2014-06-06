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
        short streamId = byteBuffer.getShort();
        byte opcode = byteBuffer.get();
        int length = byteBuffer.getInt();
        byte[] body = new byte[length];
        byteBuffer.get(body);
        this.header = new Header(Version.valueOf(version), flags, streamId, Opcode.valueOf(opcode));
        this.length = length;
        this.body = body;
    }

    public Frame(Header header, byte[] body) {
        this.header = header;
        this.body = body;
        this.length = body != null ? body.length : 0;
    }

    public static class Builder {
        public Header.Builder headerBuilder = new Header.Builder();
        public byte[] body;

        public Builder version(Version v) {
            this.headerBuilder = headerBuilder.version(v);
            return this;
        }

        public Builder flags(byte f) {
            this.headerBuilder = headerBuilder.flags(f);
            return this;
        }

        public Builder streamId(short s) {
            this.headerBuilder = headerBuilder.streamId(s);
            return this;
        }

        public Builder opcode(Opcode o) {
            this.headerBuilder = headerBuilder.opcode(o);
            return this;
        }

        public Builder body(byte[] b) {
            this.body = b;
            return this;
        }

        public Frame build() {
            return new Frame(this.headerBuilder.build(), this.body);
        }

    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + this.length);
        byte[] headerBytes = new byte[9];
        headerBytes[0] = this.header.version.value;
        headerBytes[1] = this.header.flags;
        headerBytes[2] = (byte) (this.header.streamId >>> 8);
        headerBytes[3] = (byte) (0xff & this.header.streamId);
        headerBytes[4] = this.header.opcode.value;
        headerBytes[5] = (byte) (this.length >>> 24);
        headerBytes[6] = (byte) (0xff & (this.length >>> 16));
        headerBytes[7] = (byte) (0xff & (this.length >>> 8));
        headerBytes[8] = (byte) (0xff & this.length);
        byteBuffer.put(headerBytes);
        if (this.body != null) {
            byteBuffer.put(this.body);
        }
        return byteBuffer;
    }


}


