package lab.cmego.com.cmegoclientandroid.model.Vehicle;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class EmbeddedBeaconIdentifier extends VehicleIdentifier{

    private String mMacAddress;
    private String mName;

    protected EmbeddedBeaconIdentifier() {
        super(Type.EMBEDDED_BEACON);
    }

    public EmbeddedBeaconIdentifier(String macAddress, String name) {
        this();
        mMacAddress = macAddress;
        mName = name;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public void setMacAddress(String macAddress) {
        mMacAddress = macAddress;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return "Beacon: \nName: " + mName + "\nMac Address: " + mMacAddress;
    }

}
