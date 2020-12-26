package com.gwtsz.android.rxbus;

import io.reactivex.observers.DefaultObserver;

/**
 * @author jett
 * @since 2018-11-29.
 */
public abstract class SimpleObserver<T> extends DefaultObserver<T> {

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }

}
