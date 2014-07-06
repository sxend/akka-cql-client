package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;

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
