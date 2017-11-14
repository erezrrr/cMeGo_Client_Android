package lab.cmego.com.cmegoclientandroid.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

public class SlideButton extends android.support.v7.widget.AppCompatSeekBar {

    private static final int MIN_SLIDE_LIMIT = 70;
    private SlideButtonListener mListener;

    public SlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getThumb().getBounds().contains((int) event.getX(), (int) event.getY())) {
                super.onTouchEvent(event);
            } else
                return false;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getProgress() > MIN_SLIDE_LIMIT){
                handleSlide();
            }

            animateClosed();

        } else {
            super.onTouchEvent(event);
        }

        return true;
    }

    private void animateClosed() {
        ValueAnimator anim = ValueAnimator.ofInt(getProgress(), 0);
//            anim.setDuration(2000);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animProgress = (Integer) animation.getAnimatedValue();
                setProgress(animProgress);
            }
        });
        anim.start();
    }

    private void handleSlide() {
        if(mListener != null){
            mListener.onSlide();
        }
    }

    public void setSlideButtonListener(SlideButtonListener listener) {
        mListener = listener;
    }

    public interface SlideButtonListener {
        void onSlide();
    }
}

