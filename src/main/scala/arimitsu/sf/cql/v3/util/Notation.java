package arimitsu.sf.cql.v3.util;

import arimitsu.sf.cql.v3.Consistency;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by sxend on 14/06/07.
 */
public class Notation {

    public static class OptionNotation {
        public final short id;
        public final Object value;

        public OptionNotation(short id, Object value) {
            this.id = id;
            this.value = value;
        }
    }

    public static short getShort(ByteBuffer buffer) {
        return buffer.getShort();
    }

    public static String getString(ByteBuffer buffer) {
        return getString(buffer, buffer.getShort());
    }

    public static final String EMPTY = "";

    public static String getString(ByteBuffer buffer, int length) {
        if (length <= 0) return EMPTY;
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes);
    }

    public static String getLongString(ByteBuffer buffer) {
        return getString(buffer, buffer.getInt());
    }

    public static UUID getUUID(ByteBuffer buffer) {
        byte[] bytes = new byte[16];
        buffer.get(bytes);
        return UUID.nameUUIDFromBytes(bytes);
    }

    public static List<String> getStringList(ByteBuffer buffer) {
        List<String> list = new ArrayList<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            list.add(getString(buffer));
        }
        return list;
    }

    public static byte[] getBytes(ByteBuffer buffer) {
        return getBytes(buffer, buffer.getInt());
    }

    public static byte[] getShortBytes(ByteBuffer buffer) {
        return getBytes(buffer, buffer.getShort());
    }

    public static byte[] getBytes(ByteBuffer buffer, int length) {
        if (length > 0) {
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return bytes;
        } else {
            return null;
        }
    }

    public static OptionNotation getOption(ByteBuffer buffer) {
        short id = getShort(buffer);
        return new OptionNotation(id, null);
    }

    public static List<OptionNotation> getOptionList(ByteBuffer buffer) {
        List<OptionNotation> list = new ArrayList<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            list.add(getOption(buffer));
        }
        return list;
    }

    public static InetSocketAddress getINet(ByteBuffer buffer) {
        byte[] addrArea = new byte[buffer.get()];
        buffer.get(addrArea);
        return InetSocketAddress.createUnresolved(new String(addrArea), buffer.getInt());
    }

    public static Consistency getConsistency(ByteBuffer buffer) {
        return Consistency.valueOf(getShort(buffer));
    }

    public static Map<String, String> getStringMap(ByteBuffer buffer) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            map.put(getString(buffer), getString(buffer));
        }
        return map;
    }

    public static Map<String, List<String>> getStringMultiMap(ByteBuffer buffer) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            map.put(getString(buffer), getStringList(buffer));
        }
        return map;
    }

    public static byte[] toStringMap(Map<String, String> maps) {
        byte[] mapLength = short2Bytes((short) maps.size());
        byte[] result = new byte[0];
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            result = join(result, join(toString(entry.getKey()), toString(entry.getValue())));
        }
        return join(mapLength, result);
    }

    public static byte[] toString(String str) {
        byte[] bytes;
        try {
            bytes = str.getBytes("UTF-8");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        int length = bytes.length;
        return join(short2Bytes((short) length), bytes);
    }

    public static byte[] short2Bytes(short s) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (0xff & (s >> 8));
        bytes[1] = (byte) (0xff & s);
        return bytes;
    }

    static byte[] join(byte[] byte1, byte[] byte2) {
        byte[] resultBytes = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, resultBytes, 0, byte1.length);
        System.arraycopy(byte2, 0, resultBytes, byte1.length, byte2.length);
        return resultBytes;
    }
}
