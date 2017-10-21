package lab.cmego.com.cmegoclientandroid.model.Events;

import lab.cmego.com.cmegoclientandroid.model.Constants.VehicleAuthenticationMethod;

import static lab.cmego.com.cmegoclientandroid.model.Events.BaseEvent.Type.VEHICLE_AUTHENTICATION;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class VehicleAuthenticationEvent extends AuthenticationEvent {

    private VehicleAuthenticationMethod mVehicleAuthenticationMethod;

    protected VehicleAuthenticationEvent() {
        super(VEHICLE_AUTHENTICATION);
    }
}
