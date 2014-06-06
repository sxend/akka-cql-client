package arimitsu.sf.cql.v3.parser;

/**
 * Created by sxend on 2014/06/06.
 */
public class StartupParser implements BodyParser<Startup> {

    public static final byte[] CQL_VERSION = "CQL_VERSION".getBytes();
    public static final byte[] V3 = "3.0.0".getBytes();
    public static final byte[] COMPRESSION = "COMPRESSION".getBytes();
    public static final byte[] LZ4 = "lz4".getBytes();
    public static final byte[] SNAPPY = "snappy".getBytes();

    @Override
    public Startup parse(byte[] body) {
        return null;
    }

    @Override
    public byte[] parse(Startup startup) {
        return null;
    }
}

class Startup {
    public static enum Options{
        CQL_VERSION,
        COMPRESSION
    }
}