package arimitsu.sf.cql.v3.columntype;

public class CounterType implements ColumnType {
    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.COUNTER;
    }

    @Override
    public Parser<?> getParser() {
        return null;
    }
}
