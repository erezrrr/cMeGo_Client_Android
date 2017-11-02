package lab.cmego.com.cmegoclientandroid.serialization;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

/**
 * Created by Owner on 02/11/2017.
 */

public class CustomGson {

    private static CustomGson sInstance;

    private Gson mGson;

    private CustomGson(){
        FieldNamingStrategy customPolicy = new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                if(!f.getName().startsWith("m")){
                    return f.getName();
                }

                return f.getName().substring(1, 2).toLowerCase() + f.getName().substring(2);
            }
        };

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingStrategy(customPolicy);
        mGson = gsonBuilder.create();
    }

    public static Gson getInstance(){
        if(sInstance == null){
            sInstance = new CustomGson();
        }

        return sInstance.getGson();
    }

    public Gson getGson() {
        return mGson;
    }
}
