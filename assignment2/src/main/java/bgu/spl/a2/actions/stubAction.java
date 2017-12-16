package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;

/**
 * Created by מחשב on 16/12/2017.
 */
public class stubAction extends Action {

    boolean result;

    public stubAction(boolean result){
        this.result = result;
        promise = new Promise();
    }

    @Override
    protected void start() {
        promise.resolve(result);
    }
}
