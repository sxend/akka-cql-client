package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.Parser;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sxend on 2014/06/17.
 */
public class MapType implements ColumnType {
    public final ColumnType keyType;
    public final ColumnType valueType;
    private final Parser<Map<Object, Object>> PARSER = new Parser<Map<Object, Object>>() {
        @Override
        public Map<Object, Object> parse(ByteBuffer buffer) {
            Parser<?> keyParser = keyType.getParser();
            Parser<?> valueParser = valueType.getParser();
            Map<Object, Object> map = new HashMap<>();
            int byteLength = buffer.getInt();
            int length = buffer.getInt();
            for (int i = 0; i < length; i++) {
                map.put(keyParser.parse(buffer), valueParser.parse(buffer));
            }
            return map;
        }
    };

    public MapType(ColumnType keyType, ColumnType valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    @Override
    public short getId() {
        return ColumnTypeEnum.MAP.id;
    }

    @Override
    public Parser<Map<Object, Object>> getParser() {
        return PARSER;
    }
}