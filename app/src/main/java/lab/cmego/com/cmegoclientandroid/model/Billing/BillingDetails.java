package lab.cmego.com.cmegoclientandroid.model.Billing;

import java.util.Date;

/**
 * Created by Amit Ishai on 9/27/2017.
 */

public class BillingDetails {

    public enum Frequency { DAILY, WEEKLY, MONTHLY, YEARLY }

    private PaymentMethod mPaymentMethod;
    private float mAmount;
    private Frequency mFrequency;
    private Date mStartDate;

    public BillingDetails() {
    }

    public BillingDetails(PaymentMethod paymentMethod, float amount, Frequency frequency, Date
            startDate) {
        mPaymentMethod = paymentMethod;
        mAmount = amount;
        mFrequency = frequency;
        mStartDate = startDate;
    }

    public PaymentMethod getPaymentMethod() {
        return mPaymentMethod;
    }

    public float getAmount() {
        return mAmount;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Frequency getFrequency() {
        return mFrequency;
    }
}
