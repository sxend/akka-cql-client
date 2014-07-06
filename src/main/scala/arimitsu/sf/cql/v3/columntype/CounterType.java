package arimitsu.sf.cql.v3.columntype;

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
