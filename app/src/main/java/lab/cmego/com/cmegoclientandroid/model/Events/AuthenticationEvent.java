package lab.cmego.com.cmegoclientandroid.model.Events;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public abstract class AuthenticationEvent extends CoreEvent {

    protected AuthenticationEvent(Type type) {
        super(type);
    }

}
