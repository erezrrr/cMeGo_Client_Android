package lab.cmego.com.cmegoclientandroid.model.Vehicle;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class PlateIdentifier extends VehicleIdentifier{

    private String mNumber;

    // Country / Region
    private String mLocale;

    protected PlateIdentifier() {
        super(Type.PLATE);
    }

    public PlateIdentifier(String number, String locale) {
        this();
        mNumber = number;
        mLocale = locale;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getLocale() {
        return mLocale;
    }

    @Override
    public String toString() {
        return "Plate: \nNumber: " + mNumber + "\n" + "Locale: " + mLocale;
    }
}
