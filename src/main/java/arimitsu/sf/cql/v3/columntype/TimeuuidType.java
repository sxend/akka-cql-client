package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.UUID;

public class TimeuuidType implements ColumnType {
    private static final Parser<UUID> PARSER = new Parser<UUID>() {
        @Override
        public UUID parse(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            byte[] bytes = Notation.getBytes(buffer, length);
            return Notation.toUUID(bytes);
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.TIMEUUID;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
