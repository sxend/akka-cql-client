package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class StatusChange implements Event {
    @Override
    public EventType getType() {
        return EventType.STATUS_CHANGE;
    }

    public static StatusChange fromBuffer(ByteBuffer buffer) {
        return null;
    }
}
