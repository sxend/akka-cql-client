package arimitsu.sf.cql.v3.messages;


import arimitsu.sf.cql.v3.messages.results.columntype.AsciiType;
import arimitsu.sf.cql.v3.messages.results.columntype.ListType;
import arimitsu.sf.cql.v3.messages.results.columntype.MapType;
import arimitsu.sf.cql.v3.messages.results.columntype.SetType;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/14.
 */
public interface ColumnType {
    short getId();

    Parser<?> getParser();

    public static final Parser<ColumnType> PARSER = new Parser<ColumnType>() {
        @Override
        public ColumnType parse(ByteBuffer buffer) {
            ColumnTypeEnum typeEnum = ColumnTypeEnum.valueOf(Notation.getShort(buffer));
            switch (typeEnum) {
                case CUSTOM:
                    break;
                case ASCII:
                    return new AsciiType();
                case BIGINT:
                    break;
                case BLOB:
                    break;
                case BOOLEAN:
                    break;
                case COUNTER:
                    break;
                case DECIMAL:
                    break;
                case DOUBLE:
                    break;
                case FLOAT:
                    break;
                case INT:
                    break;
                case TIMESTAMP:
                    break;
                case UUID:
                    break;
                case VARCHAR:
                    break;
                case VARINT:
                    break;
                case TIMEUUID:
                    break;
                case INET:
                    break;
                case UDT:
                    break;
                case TUPLE:
                    break;
                case LIST:
                    return new ListType(parse(buffer));
                case SET:
                    return new SetType(parse(buffer));
                case MAP:
                    return new MapType(parse(buffer), parse(buffer));
                default:
                    return null;
            }
            return null;
        }

    };


}

