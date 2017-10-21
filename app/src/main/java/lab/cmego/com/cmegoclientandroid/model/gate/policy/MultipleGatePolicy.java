package lab.cmego.com.cmegoclientandroid.model.gate.policy;

/**
 * Created by Owner on 20/10/2017.
 */

public abstract class MultipleGatePolicy {

    public enum Type {
        OPEN_TOGETHER,
        FIXED_INTERVAL_CONSECUTIVE, //Open gates using fixed interval, in consecutive order
        AUTHORIZE_ONCE_THEN_OPEN_BY_PROXIMITY,
        BUTTON_FOR_EACH
    }

    public Type mType;

    public MultipleGatePolicy(Type type) {
        mType = type;
    }

    public Type getType() {
        return mType;
    }
}
