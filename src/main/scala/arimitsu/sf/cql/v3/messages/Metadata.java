package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sxend on 14/06/11.
 */
public class Metadata {
    // <flags><columns_count>[<paging_state>][<global_table_spec>?<col_spec_1>...<col_spec_n>]
    public final int flags;
    public final int columnsCount;
    public final byte[] pagingState;
    public final Set<Object> globalTableSpec;

    public Metadata(int flags, int columnsCount, byte[] pagingState, Set<Object> globalTableSpec) {
        this.flags = flags;
        this.columnsCount = columnsCount;
        this.pagingState = pagingState;
        this.globalTableSpec = globalTableSpec;
    }


    public static enum MetadataFlags {
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

    public static class MetadataParser {
        public Metadata parse(ByteBuffer buffer) {
            int flags = buffer.getInt();
            int count = buffer.getInt();
            boolean hasPagingState = (MetadataFlags.HAS_MORE_PAGES.mask & flags) > 0;
            boolean hasGlobalSetting = (MetadataFlags.GLOBAL_TABLES_SPEC.mask & flags) > 0;
            boolean hasntMetadata = (MetadataFlags.NO_METADATA.mask & flags) > 0;
            byte[] pagingState = null;
            if (hasPagingState) {
                pagingState = Notation.getBytes(buffer);
            }
            Set<Object> set = new HashSet<>();
            String globalSetting = null;
            if (hasGlobalSetting) {
                globalSetting = Notation.getString(buffer) + Notation.getString(buffer);
                set.add(globalSetting);
            }
            for (int i = 0; i < count; i++) {

                String keySpace = null;
                String table = null;
                if (!hasGlobalSetting) {
                    keySpace = Notation.getString(buffer);
                    table = Notation.getString(buffer);
                    set.add(keySpace + table);
                }
                String cname = Notation.getString(buffer);
                Notation.OptionNotation option = Notation.getOption(buffer, new Notation.OptionParser() {
                    @Override
                    public Object parse(ByteBuffer buffer) {
                        return null;
                    }
                });
                set.add(cname);
                set.add(option);
            }
            return new Metadata(flags, count, pagingState, set);
        }
    }
}
