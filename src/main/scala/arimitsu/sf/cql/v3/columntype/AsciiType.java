package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/17.
 */
public class AsciiType implements ColumnType {
    private static final Parser<String> PARSER = new Parser<String>() {
        @Override
        public String parse(ByteBuffer buffer) {
            return Notation.getLongString(buffer);
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.ASCII;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
