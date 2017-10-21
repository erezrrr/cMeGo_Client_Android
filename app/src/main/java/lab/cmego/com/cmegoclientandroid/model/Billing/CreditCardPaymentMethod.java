package lab.cmego.com.cmegoclientandroid.model.Billing;

import java.util.Date;

/**
 * Created by Amit Ishai on 9/27/2017.
 */

public class CreditCardPaymentMethod extends PaymentMethod {

    private String mNumber;
    private Date mExpirationDate;
    private String mCVC;

    public CreditCardPaymentMethod() {
        super(Type.CREDIT_CARD);
    }

}
