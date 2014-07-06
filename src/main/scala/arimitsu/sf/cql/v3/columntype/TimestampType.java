package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;
import java.util.Date;

public class TimestampType implements ColumnType {
    private static final Parser<Date> PARSER = new Parser<Date>() {
        @Override
        public Date parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            long time = buffer.getLong() * 1000;
            return new Date(time);
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.TIMESTAMP;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
