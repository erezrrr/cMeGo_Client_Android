package lab.cmego.com.cmegoclientandroid.model.Vehicle;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public abstract class VehicleIdentifier {

    public enum Type { NONE, PLATE, EMBEDDED_BEACON}
    private Type mType;

    protected VehicleIdentifier(Type type){
        mType = type;
    }

    public Type getType() {
        return mType;
    }
}
