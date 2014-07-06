package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class TopologyChange implements Event {
    @Override
    public EventType getType() {
        return EventType.TOPOLOGY_CHANGE;
    }

    public static TopologyChange fromBuffer(ByteBuffer buffer) {
        return null;
    }
}
