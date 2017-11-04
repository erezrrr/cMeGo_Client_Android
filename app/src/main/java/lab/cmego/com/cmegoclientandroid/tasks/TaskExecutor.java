package lab.cmego.com.cmegoclientandroid.tasks;

import android.os.Bundle;

import java.util.LinkedList;

import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;

/**
 * Created by Amit Ishai on 1/31/2017.
 */

public class TaskExecutor implements Task{
    protected LinkedList<Task> mTasks;
    protected ResultListener<Bundle> mListener;

    public TaskExecutor(){
        mTasks = new LinkedList<>();
    }

    public void addTask(Task task){
        mTasks.add(task);
    }

    public TaskExecutor withTasks(LinkedList<Task> tasks){
        mTasks = tasks;
        return this;
    }

    @Override
    public void execute(Bundle input, final ResultListener<Bundle> listener) {
        mListener = listener;
        executeNextTask(input);
    }

    private void executeNextTask(Bundle input) {

        Task nextTask = mTasks.remove();

        nextTask.execute(input, new ResultListener<Bundle>() {
            @Override
            public void onResult(Bundle output) {

                if(mTasks.isEmpty()){
                    mListener.onResult(output);
                } else {
                    executeNextTask(output);
                }

            }

            @Override
            public void onError(Exception e) {
                if(mListener != null){
                    mListener.onError(e);
                }
            }
        });
    }
}
