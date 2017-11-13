package lab.cmego.com.cmegoclientandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Switch;

/**
 * Created by Owner on 13/11/2017.
 */

public class CustomSwitchView extends Switch {
    public CustomSwitchView(Context context) {
        super(context);
    }

    public CustomSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("dfdf","dfdfdf : " + ev.getAction() + ", " + ev.getX() + ", " + ev.getY());
        Log.d("dfdf","dfdfdf thumb getButtonDrawable : " + getX() + ", " + getY());
        Log.d("dfdf","dfdfdf thumb: " + getThumbDrawable().getCurrent().getBounds().centerX() + ", " + getThumbDrawable().getCurrent().getBounds().centerY());
        Log.d("dfdf","dfdfdf thumb contains: " + getThumbDrawable().getCurrent().getBounds().contains((int)ev.getX(), (int)ev.getY()));

        return super.onTouchEvent(ev);
    }
}
