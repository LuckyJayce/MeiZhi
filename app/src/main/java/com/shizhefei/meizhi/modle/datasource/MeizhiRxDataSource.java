package com.shizhefei.meizhi.modle.datasource;

import com.shizhefei.meizhi.modle.datasource.common.MRxDataSource;
import com.shizhefei.meizhi.modle.entry.BaseData;
import com.shizhefei.meizhi.modle.entry.Meizhi;

import java.util.List;

import rx.functions.Action0;

/**
 * Created by LuckyJayce on 2016/7/21.
 */
public class MeizhiRxDataSource extends MRxDataSource<List<Meizhi>> {
    private int mPage;

    @Override
    public ObservableAction<BaseData<List<Meizhi>>> refreshRXM() throws Exception {
        return load(mPage);
    }

    @Override
    public ObservableAction<BaseData<List<Meizhi>>> loadMoreRXM() throws Exception {
        return load(mPage + 1);
    }

    private ObservableAction<BaseData<List<Meizhi>>> load(final int page) throws Exception {
        return new ObservableAction<>(getGankApi().getMeizhiData(page), new Action0() {
            @Override
            public void call() {
                mPage = page;
            }
        });
    }


    @Override
    public boolean hasMore() {
        return true;
    }
}
