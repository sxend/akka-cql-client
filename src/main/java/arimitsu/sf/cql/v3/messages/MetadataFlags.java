package arimitsu.sf.cql.v3.messages;

/**
 * Created by sxend on 14/07/06.
 */
public enum MetadataFlags {
    GLOBAL_TABLES_SPEC(0x0001),
    HAS_MORE_PAGES(0x0002),
    NO_METADATA(0x0004);

    public final int mask;

    MetadataFlags(int mask) {
        this.mask = mask;
    }

    public static MetadataFlags valueOf(int mask) {
        for (MetadataFlags f : values()) {
            if (f.mask == mask) return f;
        }
        throw new RuntimeException("invalid mask");
    }
}
