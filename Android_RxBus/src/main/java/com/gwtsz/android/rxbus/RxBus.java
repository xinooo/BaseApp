package com.gwtsz.android.rxbus;

import com.gwtsz.android.rxbus.event.RxBusBaseMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by ari on 17-7-7.
 */

public class RxBus {
    private static volatile RxBus instance;
    private final FlowableProcessor<Object> mBus;

    private final Map<String, Object> mStickyEventMap;

    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 发送事件
     */
    public void post(Object event) {
        mBus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Flowable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasSubscribers();
    }

    public void reset() {
        instance = null;
    }

    public void post(String code, Object o) {
        mBus.onNext(new RxBusBaseMessage(code, o));
    }

    public <T> Flowable<T> register(final String code, final Class<T> eventType) {
        return mBus.ofType(RxBusBaseMessage.class)
                .filter(new Predicate<RxBusBaseMessage>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull RxBusBaseMessage rxBusBaseMessage) throws Exception {
                        return rxBusBaseMessage.getCode().equals(code) && eventType.isInstance(rxBusBaseMessage.getObject());
                    }
                }).map(new Function<RxBusBaseMessage, Object>() {
                    @Override
                    public Object apply(RxBusBaseMessage o) {
                        return o.getObject();
                    }
                }).cast(eventType).onBackpressureLatest().observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Flowable<T> registerDelay(final String code, final Class<T> eventType, long delayMs) {
        return mBus.ofType(RxBusBaseMessage.class)
                .delay(delayMs, TimeUnit.MICROSECONDS)
                .filter(new Predicate<RxBusBaseMessage>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull RxBusBaseMessage rxBusBaseMessage) throws Exception {
                        return rxBusBaseMessage.getCode().equals(code) && eventType.isInstance(rxBusBaseMessage.getObject());
                    }
                }).map(new Function<RxBusBaseMessage, Object>() {
                    @Override
                    public Object apply(RxBusBaseMessage o) {
                        return o.getObject();
                    }
                }).cast(eventType).onBackpressureLatest().observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * <p>
     * 支持延迟时间
     */
    public <T> Flowable<T> registerToObservableStickyDelay(String code, final Class<T> eventType, long delayMs) {
        synchronized (mStickyEventMap) {
            Flowable<T> observable = registerDelay(code, eventType, delayMs);
            final Object event = mStickyEventMap.get(code);

            if (event != null) {
                return observable.mergeWith(Flowable.just(eventType.cast(event)));
            } else {
                return observable;
            }
        }
    }

    /**
     * Stciky 相关
     */

    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(String code, Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(code, event);
        }
        post(code, event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Flowable<T> registertoObservableSticky(String code, final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Flowable<T> observable = register(code, eventType);
            final Object event = mStickyEventMap.get(code);

            if (event != null) {
                return observable.mergeWith(Flowable.just(eventType.cast(event)));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 清除订阅
     */
    public void clear() {
        mStickyEventMap.clear();
        mBus.onComplete();
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}
