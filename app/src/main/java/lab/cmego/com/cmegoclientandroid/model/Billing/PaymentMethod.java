package lab.cmego.com.cmegoclientandroid.model.Billing;

/**
 * Created by Amit Ishai on 9/27/2017.
 */

public abstract class PaymentMethod {

    public enum Type { NONE, CASH, CREDIT_CARD }
    private Type mType;

    public PaymentMethod() {
    }

    protected PaymentMethod(Type type){
        mType = type;
    }

    public Type getType() {
        return mType;
    }
}
