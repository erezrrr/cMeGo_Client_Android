package lab.cmego.com.cmegoclientandroid.model.gate.policy;

/**
 * Created by Owner on 22/10/2017.
 */

public class AuthorizeOnceThenOpenByProximityPolicy extends MultipleGatePolicy {

    public AuthorizeOnceThenOpenByProximityPolicy() {
        super(Type.AUTHORIZE_ONCE_THEN_OPEN_BY_PROXIMITY);
    }
}
