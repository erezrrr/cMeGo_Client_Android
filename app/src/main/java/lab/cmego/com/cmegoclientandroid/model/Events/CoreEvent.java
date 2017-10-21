package lab.cmego.com.cmegoclientandroid.model.Events;


import lab.cmego.com.cmegoclientandroid.model.Constants.PassageDirection;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public abstract class CoreEvent extends BaseEvent {

    private PassageDirection mPassageDirection;
    private String mMembershipId;

    protected CoreEvent(Type type) {
        super(type);
    }
}
