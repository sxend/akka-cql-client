package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.Parser;

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
    public short getId() {
        return ColumnTypeEnum.TIMESTAMP.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
