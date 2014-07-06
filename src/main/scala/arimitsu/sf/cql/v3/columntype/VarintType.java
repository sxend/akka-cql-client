package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.util.Notation;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class VarintType implements ColumnType {
    private static final Parser<BigDecimal> PARSER = new Parser<BigDecimal>() {
        @Override
        public BigDecimal parse(ByteBuffer buffer) {
            int length = buffer.getInt(); // length 4
            byte[] bytes = Notation.getBytes(buffer, length);
            return BigDecimal.valueOf(Notation.getLong(bytes));
        }
    };

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.VARINT;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
