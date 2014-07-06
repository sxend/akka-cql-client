package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

public class BlobType implements ColumnType {
    private static final Parser<byte[]> PARSER = new Parser<byte[]>() {
        @Override
        public byte[] parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notation.getBytes(buffer, length);
        }
    };

    @Override
    public short getId() {
        return ColumnTypeEnum.BLOB.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
