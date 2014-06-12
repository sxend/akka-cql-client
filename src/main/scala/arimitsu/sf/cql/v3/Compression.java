package arimitsu.sf.cql.v3;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by sxend on 14/06/08.
 */
public enum Compression {

    NONE("", new Compressor() {
        @Override
        public int getCommpressedLength(byte[] bytes) {
            return bytes.length;
        }

        @Override
        public byte[] compress(byte[] bytes) {
            return bytes;
        }

        @Override
        public byte[] decompress(byte[] bytes) {
            return bytes;
        }
    }),
    LZ4("lz4", new Compressor() {
        @Override
        public int getCommpressedLength(byte[] bytes) {
            return bytes.length - 4;
        }

        @Override
        public byte[] compress(byte[] bytes) {
            int length = bytes.length;
            LZ4Factory factory = LZ4Factory.fastestInstance();
            LZ4Compressor compressor = factory.fastCompressor();
            int maxLength = compressor.maxCompressedLength(length);
            byte[] compressed = new byte[maxLength + 4];
            compressed[0] = (byte) (0xff &(length >> 24));
            compressed[1] = (byte) (0xff & (length >> 16));
            compressed[2] = (byte) (0xff & (length >> 8));
            compressed[3] = (byte) (0xff & length);
            compressor.compress(bytes, 0, length, compressed, 4, maxLength);
            return compressed;
        }

        @Override
        public byte[] decompress(byte[] bytes) {
            LZ4Factory factory = LZ4Factory.fastestInstance();
            LZ4FastDecompressor decompressor = factory.fastDecompressor();
            int decompressedLength = (bytes[0] << 24) |
                    (bytes[1] << 16) |
                    (bytes[2] << 8) |
                    (bytes[3]);
            byte[] decompressed = new byte[decompressedLength];
            decompressor.decompress(bytes, 4, decompressed, 0, decompressedLength);
            return decompressed;
        }
    }),
    SNAPPY("snappy", new Compressor() {
        @Override
        public int getCommpressedLength(byte[] bytes) {
            return bytes.length;
        }

        @Override
        public byte[] compress(byte[] bytes) {
            try {
                return Snappy.compress(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public byte[] decompress(byte[] bytes) {
            try {
                return Snappy.uncompress(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }),;

    public static final String KEY = "COMPRESSION";
    public final String name;
    public final Compressor compressor;

    Compression(String name, Compressor compressor) {
        this.name = name;
        this.compressor = compressor;
    }

    public static Compression valueOf(byte[] name) {
        String strName = new String(name);
        for (Compression c : values()) {
            if (c.name.equals(strName)) return c;
        }
        return NONE;
    }

    public static interface Compressor {
        public int getCommpressedLength(byte[] bytes);
        public byte[] compress(byte[] bytes);

        public byte[] decompress(byte[] bytes);
    }
}

