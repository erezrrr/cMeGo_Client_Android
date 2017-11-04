package lab.cmego.com.cmegoclientandroid.authentication;

/**
 * Created by Owner on 04/11/2017.
 */

public class AuthenticationResult {

    private boolean mSuccess;
    private boolean mOpening;
    private boolean mNeedAdditionalAuth;

    public boolean isSuccess() {
        return mSuccess;
    }

    @Override
    public String toString() {
        return "Success: " + mSuccess + "\n" +
                "Opening: " + mOpening + "\n" +
                "NeedAdditionalAuth: " + mNeedAdditionalAuth;
    }
}
