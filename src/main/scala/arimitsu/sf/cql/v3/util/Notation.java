package arimitsu.sf.cql.v3.util;

import arimitsu.sf.cql.v3.Consistency;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sxend on 14/06/07.
 */
public class Notation {

    public static class OptionNotation<A> {
        public final short id;
        public final A value;

        public OptionNotation(short id, A value) {
            this.id = id;
            this.value = value;
        }
    }

    public static interface OptionParser<A> {
        public A parse(ByteBuffer buffer);
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

    private static final Constructor<UUID> uuidConstructor;

    static {
        Constructor<UUID> c = null;
        try {
            c = UUID.class.getDeclaredConstructor(byte[].class);
            c.setAccessible(true);
        } catch (NoSuchMethodException e) {
        }
        uuidConstructor = c;
    }

    public static UUID toUUID(byte[] bytes) {
        try {
            return uuidConstructor.newInstance(bytes);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getStringList(ByteBuffer buffer) {
        List<String> list = new ArrayList<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            list.add(getString(buffer));
        }
        return list;
    }

    public static List<String> getLongStringList(ByteBuffer buffer) {
        List<String> list = new ArrayList<>();
        for (int i = 0, length = buffer.getInt(); i < length; i++) {
            list.add(getLongString(buffer));
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

    public static <A> OptionNotation<A> getOption(ByteBuffer buffer, OptionParser<A> parser) {
        short id = getShort(buffer);
        return new OptionNotation<>(id, parser.parse(buffer));
    }

    public static <A> List<OptionNotation<A>> getOptionList(ByteBuffer buffer, OptionParser<A> parser) {
        List<OptionNotation<A>> list = new ArrayList<>();
        for (int i = 0, length = buffer.getShort(); i < length; i++) {
            list.add(getOption(buffer, parser));
        }
        return list;
    }

    public static InetAddress getINet(ByteBuffer buffer) {
        byte[] addrArea = new byte[buffer.get()];
        buffer.get(addrArea);
        return toInet(addrArea);
    }

    public static InetAddress toInet(byte[] bytes) {
        try {
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
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

    public static byte[] toLongString(String str) {
        byte[] bytes;
        try {
            bytes = str.getBytes("UTF-8");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        return join(int2Bytes(bytes.length), bytes);
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
        bytes[0] = (byte) (0xff & (s >>> 8));
        bytes[1] = (byte) (0xff & s);
        return bytes;
    }

    public static byte[] int2Bytes(int s) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & (s >>> 24));
        bytes[1] = (byte) (0xff & (s >>> 16));
        bytes[2] = (byte) (0xff & (s >>> 8));
        bytes[3] = (byte) (0xff & s);
        return bytes;
    }

    public static byte[] long2Bytes(long s) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (0xff & (s >>> 56));
        bytes[1] = (byte) (0xff & (s >>> 48));
        bytes[2] = (byte) (0xff & (s >>> 40));
        bytes[3] = (byte) (0xff & (s >>> 32));
        bytes[4] = (byte) (0xff & (s >>> 24));
        bytes[5] = (byte) (0xff & (s >>> 16));
        bytes[6] = (byte) (0xff & (s >>> 8));
        bytes[7] = (byte) (0xff & s);
        return bytes;
    }

    public static byte[] join(byte[] byte1, byte[] byte2) {
        byte[] resultBytes = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, resultBytes, 0, byte1.length);
        System.arraycopy(byte2, 0, resultBytes, byte1.length, byte2.length);
        return resultBytes;
    }

    public static long getLong(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result += ((0xff & bytes[i]) << ((bytes.length - i - 1) * 8));
        }
        return result;
    }
}
