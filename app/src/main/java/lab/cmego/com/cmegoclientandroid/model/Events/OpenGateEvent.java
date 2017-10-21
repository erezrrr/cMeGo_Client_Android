package lab.cmego.com.cmegoclientandroid.model.Events;

import static lab.cmego.com.cmegoclientandroid.model.Events.BaseEvent.Type.OPEN_GATE;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class OpenGateEvent extends CoreEvent{

    protected OpenGateEvent() {
        super(OPEN_GATE);
    }
}
