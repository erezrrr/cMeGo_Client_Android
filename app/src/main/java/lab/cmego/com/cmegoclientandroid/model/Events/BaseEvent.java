package lab.cmego.com.cmegoclientandroid.model.Events;

import java.util.Date;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public abstract class BaseEvent {

    public enum Type { USER_AUTHENTICATION, VEHICLE_AUTHENTICATION, OPEN_GATE, VEHICLE_PASSAGE, GATE_CLOSED }

    private Type mEventType;
    private Date mDate;

    protected BaseEvent(Type eventType){
        mEventType = eventType;
    }
}
