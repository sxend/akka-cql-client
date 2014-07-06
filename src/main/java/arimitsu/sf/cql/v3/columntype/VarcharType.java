package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

public class VarcharType implements ColumnType {
    private static final Parser<String> PARSER = new Parser<String>() {
        @Override
        public String parse(ByteBuffer buffer) {
            return Notation.getString(buffer, buffer.getInt());
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.VARCHAR;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
