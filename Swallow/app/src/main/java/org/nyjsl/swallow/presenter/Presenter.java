package org.nyjsl.swallow.presenter;

import org.nyjsl.swallow.views.BaseView;

/**
 * Interface that represents a Presenter in the model view presenter Pattern
 * defines methods to manage the Activity / Fragment lifecycle
 */
public abstract class Presenter {

    protected BaseView mView ;

    /**
     * Called when the presenter is initialized
     */
    public abstract void start ();

    /**
     * Called when the presenter is stop, i.e when an activity
     * or a fragment finishes
     */
    public abstract void stop ();

    public  void attachView(BaseView view){
        this.mView = view;
    }
}