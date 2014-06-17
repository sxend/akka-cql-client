package arimitsu.sf.cql.v3.messages.results.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;

import java.nio.ByteBuffer;

public class IntType implements ColumnType {
    private static final Parser<Integer> PARSER = new Parser<Integer>() {
        @Override
        public Integer parse(ByteBuffer buffer) {
            buffer.getInt(); // length 4
            return buffer.getInt();
        }
    };
    @Override
    public short getId() {
        return ColumnTypeEnum.INT.id;
    }

    @Override
    public Parser<Integer> getParser() {
        return PARSER;
    }
}
