package lab.cmego.com.cmegoclientandroid.model.Events;

import static lab.cmego.com.cmegoclientandroid.model.Events.BaseEvent.Type.GATE_CLOSED;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class GateClosedEvent extends BaseEvent {

    // Not specific to a membership or account. We really only know the location.
    // Membership is not present. There could arguably be numerous accounts to a location.
    private String mLocationId;

    protected GateClosedEvent() {
        super(GATE_CLOSED);
    }
}
