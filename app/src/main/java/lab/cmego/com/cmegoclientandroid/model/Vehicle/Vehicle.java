package lab.cmego.com.cmegoclientandroid.model.Vehicle;

import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class Vehicle {

    public enum Type { SEDAN, SUV, MOTORCYCLE, MINIVAN, BUS, SPORTS_CAR, PLANE, TRAIN, HOT_AIR_BALLOON }

    @PropertyName("id")
    private String mId;

    @PropertyName("type")
    private Type mType;

    @PropertyName("vehicleIdentifiers")
    private List<VehicleIdentifier> mVehicleIdentifiers;

    public Vehicle() {
    }

    public Vehicle(Type type, String id, List<VehicleIdentifier> vehicleIdentifiers) {
        mType = type;
        mId = id;
        mVehicleIdentifiers = vehicleIdentifiers;
    }

    public Type getType() {
        return mType;
    }

    public List<VehicleIdentifier> getVehicleIdentifiers() {
        return mVehicleIdentifiers;
    }

    public String getId() {
        return mId;
    }

    public Vehicle setId(String id) {
        mId = id;
        return this;
    }

    public Vehicle setType(Type type) {
        mType = type;
        return this;
    }

    public Vehicle setVehicleIdentifiers(List<VehicleIdentifier> vehicleIdentifiers) {
        mVehicleIdentifiers = vehicleIdentifiers;
        return this;
    }
}
