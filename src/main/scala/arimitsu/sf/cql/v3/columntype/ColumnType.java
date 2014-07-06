package arimitsu.sf.cql.v3.columntype;


import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {
    ColumnTypeEnum getEnum();

    Parser<?> getParser();

    public static class Factory {
        public static ColumnType fromBuffer(ByteBuffer buffer) {
            short id = Notation.getShort(buffer);
            ColumnTypeEnum typeEnum = ColumnTypeEnum.valueOf(id);
            switch (typeEnum) {
                case ASCII:
                    return new AsciiType();
                case BIGINT:
                    return new BigintType();
                case BLOB:
                    return new BlobType();
                case BOOLEAN:
                    return new BooleanType();
                case COUNTER:
                    return new CounterType();
                case DECIMAL:
                    return new DecimalType();
                case DOUBLE:
                    return new DoubleType();
                case FLOAT:
                    return new FloatType();
                case INT:
                    return new IntType();
                case TIMESTAMP:
                    return new TimestampType();
                case UUID:
                    return new UuidType();
                case VARCHAR:
                    return new VarcharType();
                case VARINT:
                    return new VarintType();
                case TIMEUUID:
                    return new TimeuuidType();
                case INET:
                    return new InetType();
                case LIST:
                    return new ListType(fromBuffer(buffer));
                case SET:
                    return new SetType(fromBuffer(buffer));
                case MAP:
                    return new MapType(fromBuffer(buffer), fromBuffer(buffer));
                case CUSTOM:
                    return null;
                case UDT:
                    return null;
                case TUPLE:
                    return null;
                default:
                    return null;
            }
        }
    }

    public static enum ColumnTypeEnum {
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
            for (ColumnTypeEnum cType : values()) {
                if (cType.id == id) return cType;
            }
            throw new RuntimeException("invalid id.");
        }

    }

}

