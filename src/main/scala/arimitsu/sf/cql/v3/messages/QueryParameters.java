package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Consistency;
import arimitsu.sf.cql.v3.util.Notation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static arimitsu.sf.cql.v3.util.Notation.int2Bytes;
import static arimitsu.sf.cql.v3.util.Notation.join;
import static arimitsu.sf.cql.v3.util.Notation.long2Bytes;
import static arimitsu.sf.cql.v3.util.Notation.short2Bytes;

/**
 * Created by sxend on 14/06/12.
 */
public class QueryParameters {
    public final Consistency consistency;
    public final byte flags;
    public final Values values;
    public final int resultPageSize;
    public final byte[] pagingState;
    public final Consistency serialConsistency;
    public final long timestamp;


    public QueryParameters(Consistency consistency,
                           byte flags,
                           Values values,
                           int resultPageSize,
                           byte[] pagingState,
                           Consistency serialConsistency,
                           long timestamp) {
        this.consistency = consistency;
        this.flags = flags;
        this.values = values;
        this.resultPageSize = resultPageSize;
        this.pagingState = pagingState;
        this.serialConsistency = serialConsistency;
        this.timestamp = timestamp;
    }

    public byte[] toBytes() {
        byte[] result = join(short2Bytes(consistency.level), new byte[]{flags});
        result = join(result, values.toBytes());
        result = join(result, short2Bytes(serialConsistency.level));
        result = join(result, long2Bytes(timestamp));
        return result;

    }

    public static interface Values {
        public byte[] toBytes();
    }

    public static class NamedValues implements Values {
        private final Map<String, byte[]> map = new HashMap<>();

        public void put(String name, byte[] value) {
            map.put(name, value);
        }

        @Override
        public byte[] toBytes() {
            byte[] result = new byte[0];
            for (Map.Entry<String, byte[]> entry : map.entrySet()) {

                result = join(result, join(Notation.toString(entry.getKey()), join(int2Bytes(entry.getValue().length), entry.getValue())));
            }
            return result;
        }
    }

    public static class ListValues implements Values {
        private final List<byte[]> list = new ArrayList<>();

        public void put(byte[] value) {
            list.add(value);
        }

        public void putInt(int value) {
            list.add(Notation.int2Bytes(value));
        }

        @Override
        public byte[] toBytes() {
            byte[] result = short2Bytes((short) list.size());
            for (byte[] bytes : list) {
                result = join(result, join(int2Bytes(bytes.length), bytes));
            }
            return result;
        }
    }
}
