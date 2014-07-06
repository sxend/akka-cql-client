package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.Parser;

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
    public short getId() {
        return ColumnTypeEnum.DOUBLE.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
