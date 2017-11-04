package lab.cmego.com.cmegoclientandroid.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import lab.cmego.com.cmegoclientandroid.R;

/**
 * Created by Amit Ishai on 11/30/2016.
 */

public class SettingsItem extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    String mName = "DEFAULT";
    private TextView mNameTextView;
    private Switch mSwitch;

    private CompoundButton.OnCheckedChangeListener mListener;

    public SettingsItem(Context context) {
        super(context);
        init();
    }

    public SettingsItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingsItem, 0, 0);

        try {
            mName = ta.getString(R.styleable.SettingsItem_item_name);
        } finally {
            ta.recycle();
        }

        init();
    }

    private void init(){
        inflate(getContext(), R.layout.item_settings, this);
        mNameTextView = (TextView)findViewById(R.id.name);
        mSwitch = (Switch)findViewById(R.id.toggle);

        renderState();
    }

    private void renderState() {
        mNameTextView.setText(mName);
    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        if(mListener != null){
            mListener.onCheckedChanged(compoundButton, checked);
        }

    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        mListener = listener;
    }

    public void setSwitchState(boolean checked){
        mSwitch.setOnCheckedChangeListener(null);
        mSwitch.setChecked(checked);
        mSwitch.setOnCheckedChangeListener(this);
    }

    public void setSwitchEnabled(boolean enabled) {
        mSwitch.setEnabled(enabled);
    }
}
