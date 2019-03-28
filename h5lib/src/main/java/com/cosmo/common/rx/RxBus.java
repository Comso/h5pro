package com.cosmo.common.rx;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Author:ruchao.jiang
 * Created: 2019/3/14 11:27
 * Email:ruchao.jiang@uama.com.cn
 */
public class RxBus {
    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    private RxBus() {}

    private static class Holder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public static RxBus getInstance() {
        return Holder.INSTANCE;
    }

    public void send(Object event) {
        bus.accept(event);
    }

    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.toFlowable(BackpressureStrategy.LATEST)
                .ofType(eventType);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
