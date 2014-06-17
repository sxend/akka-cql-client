package arimitsu.sf.cql.v3.messages.results.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;

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
    public short getId() {
        return ColumnTypeEnum.BIGINT.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
