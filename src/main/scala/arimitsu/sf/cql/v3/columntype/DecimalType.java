package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;
import arimitsu.sf.cql.v3.util.Notation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class DecimalType implements ColumnType {
    private static final Parser<BigDecimal> PARSER = new Parser<BigDecimal>() {
        @Override
        public BigDecimal parse(ByteBuffer buffer) {
            buffer.getInt();
            int scale = buffer.getInt();
//            byte[] bytes = Notation.getBytes(buffer,12);
            return new BigDecimal(new BigInteger(Notation.getBytes(buffer, 4)), scale);
        }
    };

    @Override
    public short getId() {
        return ColumnTypeEnum.DECIMAL.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
