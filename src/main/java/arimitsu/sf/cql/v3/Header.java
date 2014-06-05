package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public class Header {
    public final Version version;
    public final byte flags;
    public final short streamId;
    public final Opcode opcode;

    Header(Version version, byte flags, short streamId, Opcode opcode) {
        this.version = version;
        this.flags = flags;
        this.streamId = streamId;
        this.opcode = opcode;
    }

    public static class Builder {
        public Version version;
        public byte flags;
        public short streamId;
        public Opcode opcode;

        public Builder version(Version v) {
            this.version = v;
            return this;
        }

        public Builder flags(byte f) {
            this.flags = f;
            return this;
        }

        public Builder streamId(short s) {
            this.streamId = s;
            return this;
        }

        public Builder opcode(Opcode o) {
            this.opcode = o;
            return this;
        }

        public Header build() {
            return new Header(this.version, this.flags, this.streamId, this.opcode);
        }
    }
}