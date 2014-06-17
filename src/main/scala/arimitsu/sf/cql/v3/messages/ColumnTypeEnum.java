package arimitsu.sf.cql.v3.messages;

/**
 * Created by sxend on 2014/06/17.
 */
public enum ColumnTypeEnum {
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
