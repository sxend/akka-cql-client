package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {
    static enum ColumnTypeEnum implements ColumnType {
        CUSTOM(0x0000),
        ASCII(0x0001),
        BIGINT(0x0002),
        BLOB(0x0003),
        BOOLEAN(0x0004),
        COUNTER(0x0005),
        DECIMAL(0x0006),
        DOUBLE(0x0007),
        FLOAT(0x0008),
        INT(0x0009),
        TIMESTAMP(0x000B),
        UUID(0x000C),
        VARCHAR(0x000D),
        VARINT(0x000E),
        TIMEUUID(0x000F),
        INET(0x0010),
        LIST(0x0020),
        MAP(0x0021),
        SET(0x0022),
        UDT(0x0030),
        TUPLE(0x0031),;
        public final short id;

        ColumnTypeEnum(int id) {
            this.id = (short) id;
        }

        public static ColumnTypeEnum valueOf(short id) {
            for (ColumnTypeEnum e : values()) {
                if (e.id == id) return e;
            }
            throw new RuntimeException("invalid id.");
        }
        @Override
        public short getId(){
            return this.id;
        }
    }
    public short getId();
    public static class ListType implements ColumnType {
        public final ColumnType valueType;

        public ListType(ColumnType valueType) {
            this.valueType = valueType;
        }

        @Override
        public short getId() {
            return ColumnTypeEnum.LIST.id;
        }
    }

    public static class MapType implements ColumnType {
        public final ColumnType keyType;
        public final ColumnType valueType;

        public MapType(ColumnType keyType, ColumnType valueType) {
            this.keyType = keyType;
            this.valueType = valueType;
        }

        @Override
        public short getId() {
            return ColumnTypeEnum.MAP.id;
        }
    }

    public static class SetType implements ColumnType {
        public final ColumnType valueType;

        public SetType(ColumnType valueType) {
            this.valueType = valueType;
        }

        @Override
        public short getId() {
            return ColumnTypeEnum.SET.id;
        }
    }
    public static final ColumnTypeParser PARSER = new ColumnTypeParser() {
        @Override
        public ColumnType parse(ByteBuffer buffer) {
            ColumnTypeEnum typeEnum = ColumnTypeEnum.valueOf(Notation.getShort(buffer));
            switch (typeEnum) {
                case LIST:
                    return new ListType(parse(buffer));
                case SET:
                    return new SetType(parse(buffer));
                case MAP:
                    return new MapType(parse(buffer), parse(buffer));
                default:
                    return typeEnum;
            }
        }


    };
    static interface ColumnTypeParser {
        ColumnType parse(ByteBuffer buffer);
    }

}
