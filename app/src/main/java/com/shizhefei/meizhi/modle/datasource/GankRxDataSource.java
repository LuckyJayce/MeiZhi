package com.shizhefei.meizhi.modle.datasource;

import com.shizhefei.meizhi.modle.datasource.common.MRxDataSource;
import com.shizhefei.meizhi.modle.entry.BaseData;
import com.shizhefei.meizhi.modle.entry.Gank;
import com.shizhefei.utils.ArrayListMap;

import java.util.List;

/**
 * Created by LuckyJayce on 2016/7/11.
 */
public class GankRxDataSource extends MRxDataSource<ArrayListMap<String, List<Gank>>> {

    private String year;
    private String month;
    private String day;

    public GankRxDataSource(int year, int month, int day) {
        this.year = String.valueOf(year);
        this.month = String.valueOf(month);
        this.day = String.valueOf(day);
    }

    public GankRxDataSource(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public ObservableAction<BaseData<ArrayListMap<String, List<Gank>>>> refreshRXM() throws Exception {
        return new ObservableAction<>(getGankApi().getGankData(year, month, day));
    }

    @Override
    public ObservableAction<BaseData<ArrayListMap<String, List<Gank>>>> loadMoreRXM() throws Exception {
        return null;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
