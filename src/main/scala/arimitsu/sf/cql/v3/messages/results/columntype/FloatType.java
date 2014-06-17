package arimitsu.sf.cql.v3.messages.results.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;

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
    public short getId() {
        return ColumnTypeEnum.FLOAT.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
