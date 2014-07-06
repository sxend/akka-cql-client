package arimitsu.sf.cql.v3.columntype;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sxend on 2014/06/17.
 */
public class SetType implements ColumnType {
    public final ColumnType valueType;
    private final Parser<Set<Object>> PARSER = new Parser<Set<Object>>() {
        @Override
        public Set<Object> parse(ByteBuffer buffer) {
            Set<Object> set = new HashSet<>();
            int byteLength = buffer.getInt();
            int length = buffer.getInt();
            for (int i = 0; i < length; i++) {
                set.add(valueType.getParser().parse(buffer));
            }
            return set;
        }
    };

    public SetType(ColumnType valueType) {
        this.valueType = valueType;
    }

    @Override
    public ColumnTypeEnum getEnum() {
        return ColumnTypeEnum.SET;
    }

    @Override
    public Parser<Set<Object>> getParser() {
        return PARSER;
    }
}
