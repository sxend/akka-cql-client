package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;

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
