package lab.cmego.com.cmegoclientandroid.vehicle;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class VehicleContract {

    public enum Type { SEDAN, SUV, MOTORCYCLE, MINIVAN, BUS, SPORTS_CAR, PLANE, TRAIN, HOT_AIR_BALLOON }

    @PropertyName("id")
    private String mId;

    @PropertyName("type")
    private Type mType;

    @PropertyName("vehicleIdentifiers")
    private List<VehicleIdentifierContract> mVehicleIdentifiers;

    public VehicleContract() {
    }

    public VehicleContract(Type type, String id, ArrayList<VehicleIdentifierContract> vehicleIdentifiers) {
        mType = type;
        mId = id;
        mVehicleIdentifiers = vehicleIdentifiers;
    }

    public Type getType() {
        return mType;
    }

    public List<VehicleIdentifierContract> getVehicleIdentifiers() {
        return mVehicleIdentifiers;
    }

    public String getId() {
        return mId;
    }
}
