package arimitsu.sf.cql.v3.messages.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/06/28.
 */
public class Columns {
    private final List<Column> list = new ArrayList<>();
    private final Map<String, Column> map = new HashMap<>();

    public void add(Column column) {
        this.list.add(column);
        this.map.put(column.name, column);
    }

    public Column indexOf(int i) {
        return this.list.get(i);
    }

    public Column get(String name) {
        return this.map.get(name);
    }

    public List<Column> getList() {
        return this.list;
    }

    public Map<String, Column> getMap() {
        return this.map;
    }
}
