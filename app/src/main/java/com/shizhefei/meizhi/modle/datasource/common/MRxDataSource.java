package com.shizhefei.meizhi.modle.datasource.common;

import com.shizhefei.meizhi.modle.GankApi;
import com.shizhefei.meizhi.modle.entry.BaseData;
import com.shizhefei.meizhi.modle.exception.BizException;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by LuckyJayce on 2016/7/22.
 */
public abstract class MRxDataSource<DATA> extends RxDataSource<DATA> {
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit;
    private static GankApi gankApi;

    protected GankApi getGankApi() {
        if (gankApi == null) {
            synchronized (this) {
                if (gankApi == null) {
                    //手动创建一个OkHttpClient并设置超时时间
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                    retrofit = new Retrofit.Builder()
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl("http://gank.io/api/")
                            .build();
                    gankApi = retrofit.create(GankApi.class);
                }
            }
        }
        return gankApi;
    }

    @Override
    public ObservableAction<DATA> refreshRX() throws Exception {
        return load(refreshRXM());
    }

    @Override
    public ObservableAction<DATA> loadMoreRX() throws Exception {
        return load(loadMoreRXM());
    }

    private ObservableAction<DATA> load(ObservableAction<BaseData<DATA>> observableAction) {
        Observable<DATA> observable = observableAction.observable.flatMap(new Func1<BaseData<DATA>, Observable<DATA>>() {
            @Override
            public Observable<DATA> call(BaseData<DATA> baseData) {
                if (baseData.error) {
                    return Observable.error(new BizException("业务出错"));
                }
                return Observable.just(baseData.results);
            }
        });
        return new ObservableAction<DATA>(observable, observableAction.action);
    }

    public abstract ObservableAction<BaseData<DATA>> refreshRXM() throws Exception;

    public abstract ObservableAction<BaseData<DATA>> loadMoreRXM() throws Exception;
}
