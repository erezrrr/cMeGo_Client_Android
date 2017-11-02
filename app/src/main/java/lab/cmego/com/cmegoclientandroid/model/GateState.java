package lab.cmego.com.cmegoclientandroid.model;

import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;

/**
 * Created by Owner on 02/11/2017.
 */

public class GateState {

    private String mGateId;

    private String mUserId;

    private UserAuthenticationMethod mNextUserAuthenticationMethod;

    private boolean mIsMembershipActive;

    private String mOptionalMessage;
    private boolean mShowMultiple;
    private boolean mOpeningAutomatically;
    private boolean mIsOpen;

    public String getGateId() {
        return mGateId;
    }

    public void setGateId(String gateId) {
        mGateId = gateId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public UserAuthenticationMethod getNextUserAuthenticationMethod() {
        return mNextUserAuthenticationMethod;
    }

    public void setNextUserAuthenticationMethod(UserAuthenticationMethod nextUserAuthenticationMethod) {
        mNextUserAuthenticationMethod = nextUserAuthenticationMethod;
    }

    public boolean isMembershipActive() {
        return mIsMembershipActive;
    }

    public void setMembershipActive(boolean membershipActive) {
        mIsMembershipActive = membershipActive;
    }

    public String getOptionalMessage() {
        return mOptionalMessage;
    }

    public void setOptionalMessage(String optionalMessage) {
        mOptionalMessage = optionalMessage;
    }

    public boolean isShowMultiple() {
        return mShowMultiple;
    }

    public void setShowMultiple(boolean showMultiple) {
        mShowMultiple = showMultiple;
    }

    public boolean isOpeningAutomatically() {
        return mOpeningAutomatically;
    }

    public void setOpeningAutomatically(boolean openingAutomatically) {
        mOpeningAutomatically = openingAutomatically;
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    public void setOpen(boolean open) {
        mIsOpen = open;
    }
}
