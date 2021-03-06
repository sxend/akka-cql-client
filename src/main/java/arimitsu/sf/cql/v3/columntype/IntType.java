package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class IntType implements ColumnType {
    private static final Parser<Integer> PARSER = new Parser<Integer>() {
        @Override
        public Integer parse(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            return buffer.getInt();
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.INT;
    }

    @Override
    public Parser<Integer> getParser() {
        return PARSER;
    }
}
