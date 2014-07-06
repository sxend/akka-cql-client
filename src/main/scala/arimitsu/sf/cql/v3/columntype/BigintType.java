package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class BigintType implements ColumnType {
    private static final Parser<Long> PARSER = new Parser<Long>() {
        @Override
        public Long parse(ByteBuffer buffer) {
            buffer.getInt(); // length 4
            return buffer.getLong();
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.BIGINT;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
