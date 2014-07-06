package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class TopologyChange implements Event {

    public final ChangeType changeType;
    public final InetAddress nodeAddress;

    public TopologyChange(ChangeType changeType, InetAddress nodeAddress) {
        this.changeType = changeType;
        this.nodeAddress = nodeAddress;
    }

    public static enum ChangeType {
        NEW_NODE,
        REMOVED_NODE,;
    }


    @Override
    public EventType getType() {
        return EventType.TOPOLOGY_CHANGE;
    }

    public static TopologyChange fromBuffer(ByteBuffer buffer) {
        ChangeType changeType = ChangeType.valueOf(Notation.getString(buffer));
        InetAddress nodeAddress = Notation.getINet(buffer);
        return new TopologyChange(changeType, nodeAddress);
    }
}
