package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;

public class DoubleType implements ColumnType {
    private static final Parser<Double> PARSER = new Parser<Double>() {
        @Override
        public Double parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            return buffer.getDouble();
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.DOUBLE;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
