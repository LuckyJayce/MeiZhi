package com.shizhefei.meizhi.modle.datasource;

import com.shizhefei.meizhi.modle.entry.Gank;
import com.shizhefei.meizhi.modle.parser.MeizhiParser;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.shizhefei.mvc.http.UrlBuilder;
import com.shizhefei.mvc.http.okhttp.GetMethod;
import com.shizhefei.utils.ArrayListMap;

import java.util.List;

import okhttp3.Response;

/**
 * Created by LuckyJayce on 2016/7/11.
 */
public class GankDatasource implements IAsyncDataSource<ArrayListMap<String, List<Gank>>> {

    private String year;
    private String month;
    private String day;

    public GankDatasource(int year, int month, int day) {
        this.year = String.valueOf(year);
        this.month = String.valueOf(month);
        this.day = String.valueOf(day);
    }

    public GankDatasource(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public RequestHandle refresh(ResponseSender<ArrayListMap<String, List<Gank>>> sender) throws Exception {
        String url = new UrlBuilder("http://gank.io/api/day").sp(year).sp(month).sp(day).build();
        GetMethod method = new GetMethod(url);
        method.executeAsync(sender, new MeizhiParser<ArrayListMap<String, List<Gank>>>() {
            @Override
            protected void onParse(Response responses, ArrayListMap<String, List<Gank>> stringListArrayListMap) {

            }
        });
        return method;
    }

    @Override
    public RequestHandle loadMore(ResponseSender<ArrayListMap<String, List<Gank>>> sender) throws Exception {
        return null;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
