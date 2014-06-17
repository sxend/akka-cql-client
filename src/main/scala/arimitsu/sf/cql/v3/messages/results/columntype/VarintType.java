package arimitsu.sf.cql.v3.messages.results.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;
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
    public short getId() {
        return ColumnTypeEnum.VARINT.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
