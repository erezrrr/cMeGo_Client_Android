package lab.cmego.com.cmegoclientandroid.slidingwindows;

/**
 * Created by Amit Ishai on 10/5/2017.
 */

public class MaxElementSlidingWindow<T> extends SlidingWindow<T>{

    private final int mMaxSize;

    public MaxElementSlidingWindow(int maxSize){
        mMaxSize = maxSize;
    }

    @Override
    protected void trim() {
        while(size() > mMaxSize){
            remove(0);
        }
    }
}
