package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.messages.Metadata;
import arimitsu.sf.cql.v3.messages.Result;
import arimitsu.sf.cql.v3.messages.parser.MetadataParser;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Prepared implements Result {
    // <id><metadata><result_metadata>
    public final byte[] id;
    public final Metadata metadata;
    public final Metadata resultMetadata;

    public Prepared(byte[] id, Metadata metadata, Metadata resultMetadata) {
        this.id = id;
        this.metadata = metadata;
        this.resultMetadata = resultMetadata;
    }

    @Override
    public Kind getKind() {
        return Kind.PREPARED;
    }

    public static final Parser<Prepared> PARSER = new Parser<Prepared>() {
        @Override
        public Prepared parse(ByteBuffer buffer) {
            return new Prepared(Notation.getShortBytes(buffer), MetadataParser.INSTANCE.parse(buffer), MetadataParser.INSTANCE.parse(buffer));
        }
    };
}
