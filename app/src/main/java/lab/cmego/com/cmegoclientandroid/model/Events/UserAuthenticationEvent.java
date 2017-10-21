package lab.cmego.com.cmegoclientandroid.model.Events;

import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;

import static lab.cmego.com.cmegoclientandroid.model.Events.BaseEvent.Type.USER_AUTHENTICATION;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class UserAuthenticationEvent extends AuthenticationEvent {

    private UserAuthenticationMethod mUserAthenticationMethod;

    protected UserAuthenticationEvent() {
        super(USER_AUTHENTICATION);
    }
}
