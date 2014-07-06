package arimitsu.sf.cql.v3.columntype;

import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.ColumnTypeEnum;
import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.util.Notation;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class InetType implements ColumnType {
    private static final Parser<InetAddress> PARSER = new Parser<InetAddress>() {
        @Override
        public InetAddress parse(ByteBuffer buffer) {
            int length = buffer.getInt();
            return Notation.toInet(Notation.getBytes(buffer, length));
        }
    };

    @Override
    public short getId() {
        return ColumnTypeEnum.INET.id;
    }

    @Override
    public Parser<?> getParser() {
        return PARSER;
    }
}
