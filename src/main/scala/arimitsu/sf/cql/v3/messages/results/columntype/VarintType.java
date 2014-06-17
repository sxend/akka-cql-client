package arimitsu.sf.cql.v3.messages.results.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;

public class VarintType implements ColumnType {
    @Override
    public short getId() {
        return ColumnTypeEnum.VARINT.id;
    }

    @Override
    public Parser<?> getParser() {
        return null;
    }
}
