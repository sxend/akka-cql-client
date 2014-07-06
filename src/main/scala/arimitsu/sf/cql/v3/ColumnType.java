package arimitsu.sf.cql.v3;


import arimitsu.sf.cql.v3.columntype.AsciiType;
import arimitsu.sf.cql.v3.columntype.BigintType;
import arimitsu.sf.cql.v3.columntype.BlobType;
import arimitsu.sf.cql.v3.columntype.BooleanType;
import arimitsu.sf.cql.v3.columntype.CounterType;
import arimitsu.sf.cql.v3.columntype.DecimalType;
import arimitsu.sf.cql.v3.columntype.DoubleType;
import arimitsu.sf.cql.v3.columntype.FloatType;
import arimitsu.sf.cql.v3.columntype.InetType;
import arimitsu.sf.cql.v3.columntype.IntType;
import arimitsu.sf.cql.v3.columntype.ListType;
import arimitsu.sf.cql.v3.columntype.MapType;
import arimitsu.sf.cql.v3.columntype.Parser;
import arimitsu.sf.cql.v3.columntype.SetType;
import arimitsu.sf.cql.v3.columntype.TimestampType;
import arimitsu.sf.cql.v3.columntype.TimeuuidType;
import arimitsu.sf.cql.v3.columntype.UuidType;
import arimitsu.sf.cql.v3.columntype.VarcharType;
import arimitsu.sf.cql.v3.columntype.VarintType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {
    short getId();

    Parser<?> getParser();

    public static class Factory {
        public static ColumnType fromBuffer(ByteBuffer buffer) {
            ColumnTypeEnum typeEnum = ColumnTypeEnum.valueOf(Notation.getShort(buffer));
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
//    public static final Parser<ColumnType> PARSER = new Parser<ColumnType>() {
//        @Override
//        public static ColumnType parse(ByteBuffer buffer) {
//            ColumnTypeEnum typeEnum = ColumnTypeEnum.valueOf(Notation.getShort(buffer));
//            switch (typeEnum) {
//                case ASCII:
//                    return new AsciiType();
//                case BIGINT:
//                    return new BigintType();
//                case BLOB:
//                    return new BlobType();
//                case BOOLEAN:
//                    return new BooleanType();
//                case COUNTER:
//                    return new CounterType();
//                case DECIMAL:
//                    return new DecimalType();
//                case DOUBLE:
//                    return new DoubleType();
//                case FLOAT:
//                    return new FloatType();
//                case INT:
//                    return new IntType();
//                case TIMESTAMP:
//                    return new TimestampType();
//                case UUID:
//                    return new UuidType();
//                case VARCHAR:
//                    return new VarcharType();
//                case VARINT:
//                    return new VarintType();
//                case TIMEUUID:
//                    return new TimeuuidType();
//                case INET:
//                    return new InetType();
//                case LIST:
//                    return new ListType(parse(buffer));
//                case SET:
//                    return new SetType(parse(buffer));
//                case MAP:
//                    return new MapType(parse(buffer), parse(buffer));
//                case CUSTOM:
//                    return null;
//                case UDT:
//                    return null;
//                case TUPLE:
//                    return null;
//                default:
//                    return null;
//            }
//        }
//
//    };


}

