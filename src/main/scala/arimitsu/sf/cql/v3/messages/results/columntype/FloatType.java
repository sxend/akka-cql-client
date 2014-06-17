package arimitsu.sf.cql.v3.messages.results.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;

public class FloatType implements ColumnType {
    @Override
    public short getId() {
        return ColumnTypeEnum.FLOAT.id;
    }

    @Override
    public Parser<?> getParser() {
        return null;
    }
}
