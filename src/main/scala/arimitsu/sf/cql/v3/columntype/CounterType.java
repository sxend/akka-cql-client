package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.Parser;

public class CounterType implements ColumnType {
    @Override
    public short getId() {
        return ColumnTypeEnum.COUNTER.id;
    }

    @Override
    public Parser<?> getParser() {
        return null;
    }
}
