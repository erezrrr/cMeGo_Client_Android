package lab.cmego.com.cmegoclientandroid.model.gate.policy;

/**
 * Created by Owner on 22/10/2017.
 */

public class FixedIntervalConsecutivePolicy extends MultipleGatePolicy {

    private long mIntervalMillies;

    public FixedIntervalConsecutivePolicy(long intervalMillies) {
        super(Type.FIXED_INTERVAL_CONSECUTIVE);
        mIntervalMillies = intervalMillies;
    }
}
