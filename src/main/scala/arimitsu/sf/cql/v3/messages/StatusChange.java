package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class StatusChange implements Event {

    public final ChangeType changeType;
    public final InetAddress nodeAddress;

    public StatusChange(ChangeType changeType, InetAddress nodeAddress) {
        this.changeType = changeType;
        this.nodeAddress = nodeAddress;
    }

    public static enum ChangeType {
        UP,
        DOWN,;
    }

    @Override
    public EventType getType() {
        return EventType.STATUS_CHANGE;
    }

    public static StatusChange fromBuffer(ByteBuffer buffer) {
        ChangeType changeType = ChangeType.valueOf(Notation.getString(buffer));
        InetAddress nodeAddress = Notation.getINet(buffer);
        return new StatusChange(changeType, nodeAddress);
    }
}
