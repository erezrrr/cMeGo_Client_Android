package lab.cmego.com.cmegoclientandroid.model.Events;

import static lab.cmego.com.cmegoclientandroid.model.Events.BaseEvent.Type.VEHICLE_PASSAGE;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class VehiclePassageEvent extends CoreEvent {

    protected VehiclePassageEvent() {
        super(VEHICLE_PASSAGE);
    }

}
