package com.shizhefei.meizhi.modle.datasource.common;

import android.util.Log;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by LuckyJayce on 2016/7/21.
 */
public abstract class RxDataSource<DATA> implements IAsyncDataSource<DATA> {

    @Override
    public RequestHandle refresh(final ResponseSender<DATA> sender) throws Exception {
        return load(sender, refreshRX());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<DATA> sender) throws Exception {
        return load(sender, loadMoreRX());
    }

    private RequestHandle load(final ResponseSender<DATA> sender, final ObservableAction<DATA> observable2) {
        final Subscriber<DATA> subscriber;
        observable2.observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber = new Subscriber<DATA>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        sender.sendError(new Exception(e));
                    }

                    @Override
                    public void onNext(DATA data) {
                        if (observable2.action != null) {
                            observable2.action.call();
                        }
                        sender.sendData(data);
                    }
                });
        return new RequestHandle() {
            @Override
            public void cancle() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.unsubscribe();
                }
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }

    public abstract ObservableAction<DATA> refreshRX() throws Exception;

    public abstract ObservableAction<DATA> loadMoreRX() throws Exception;

    public static class ObservableAction<DATA> {
        public Observable<DATA> observable;
        public Action0 action;

        public ObservableAction(Observable<DATA> observable) {
            this.observable = observable;
        }

        public ObservableAction(Observable<DATA> observable, Action0 action) {
            this.observable = observable;
            this.action = action;
        }
    }

}
