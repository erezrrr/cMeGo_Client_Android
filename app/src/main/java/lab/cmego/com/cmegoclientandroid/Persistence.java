package lab.cmego.com.cmegoclientandroid;

import android.content.Context;

/**
 * Created by Owner on 29/10/2017.
 */

public class Persistence {

    private static Persistence sInstance;
    private Context mContext;

    private Persistence(){}

    public static Persistence getInstance(){
        if(sInstance == null){
            sInstance = new Persistence();
        }

        return sInstance;
    }

    public void init(Context context){
        mContext = context;
    }
}
