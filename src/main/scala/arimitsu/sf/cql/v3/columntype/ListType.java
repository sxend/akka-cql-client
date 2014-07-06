package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.messages.Parser;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxend on 2014/06/17.
 */
public class ListType implements ColumnType {
    public final ColumnType valueType;
    private final Parser<List<Object>> PARSER = new Parser<List<Object>>() {
        @Override
        public List<Object> parse(ByteBuffer buffer) {
            int byteLength = buffer.getInt();
            int length = buffer.getInt();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                list.add(valueType.getParser().parse(buffer));
            }
            return list;
        }
    };

    public ListType(ColumnType valueType) {
        this.valueType = valueType;
    }

    @Override
    public short getId() {
        return ColumnTypeEnum.LIST.id;
    }

    @Override
    public Parser<List<Object>> getParser() {
        return PARSER;
    }
}
