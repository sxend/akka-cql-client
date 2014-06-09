package arimitsu.sf.cql.v3.messages;



import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/06/07.
 */
public class Supported {

    public static final String COMPRESSION = "COMPRESSION";
    public static final String CQL_VERSION = "CQL_VERSION";

    public final Map<String, List<String>> stringMultiMap;

    public Supported(Map<String, List<String>> stringMultiMap) {
        this.stringMultiMap = stringMultiMap;
    }
    public static final ResponseParser<Supported> SupportedParser = new ResponseParser<Supported>() {
        @Override
        public Supported parse(ByteBuffer body) {
            return new Supported(Notation.getStringMultiMap(body));
        }
    };
}

