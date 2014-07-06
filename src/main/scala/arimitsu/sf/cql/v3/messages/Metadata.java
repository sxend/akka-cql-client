package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/06/11.
 */
public class Metadata {
    // <flags><columns_count>[<paging_state>][<global_table_spec>?<col_spec_1>...<col_spec_n>]
    public final int flags;
    public final int columnsCount;
    public final byte[] pagingState;
    public final Map<String, String> globalTableSpec;
    public final List<ColumnSpec> columnSpec;

    public Metadata(int flags, int columnsCount, byte[] pagingState, Map<String, String> globalTableSpec, List<ColumnSpec> columnSpec) {
        this.flags = flags;
        this.columnsCount = columnsCount;
        this.pagingState = pagingState;
        this.globalTableSpec = globalTableSpec;
        this.columnSpec = columnSpec;
    }

}
