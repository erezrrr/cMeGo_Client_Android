package lab.cmego.com.cmegoclientandroid.vehicle;

import com.google.firebase.database.PropertyName;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class VehicleIdentifierContract {

    public enum Type { NONE, PLATE, EMBEDDED_BEACON}

    @PropertyName("type")
    private Type mType;

    // Plate
    @PropertyName("number")
    private String mNumber;
    @PropertyName("locale")
    private String mLocale;

    // Beacon
    @PropertyName("macAddress")
    private String mMacAddress;
    @PropertyName("name")
    private String mName;

    public VehicleIdentifierContract() {
    }

    protected VehicleIdentifierContract(Type type){
        mType = type;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        mLocale = locale;
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
}
