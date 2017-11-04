package lab.cmego.com.cmegoclientandroid.tasks;

import android.os.Bundle;

import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;

/**
 * Created by Amit Ishai on 1/31/2017.
 */

public interface Task {
    void execute(Bundle input, ResultListener<Bundle> listener);
}
