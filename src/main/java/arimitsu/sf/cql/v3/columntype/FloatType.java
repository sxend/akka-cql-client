package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class FloatType implements ColumnType {
    private static final Parser<Float> PARSER = new Parser<Float>() {
        @Override
        public Float parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            return buffer.getFloat();
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.FLOAT;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
