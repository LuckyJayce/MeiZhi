package com.shizhefei.meizhi.modle.datasource;

import com.shizhefei.meizhi.modle.entry.Meizhi;
import com.shizhefei.meizhi.modle.parser.MeizhiParser;
import com.shizhefei.mvc.IDataSource;
import com.shizhefei.mvc.http.UrlBuilder;
import com.shizhefei.mvc.http.okhttp.GetMethod;

import java.util.List;

import okhttp3.Response;

/**
 * Created by LuckyJayce on 2016/7/11.
 */
public class MeizhisDatasource2 implements IDataSource<List<Meizhi>> {
    private int mPage = 1;

    private List<Meizhi> load(final int page) throws Exception {
        //        http://gank.io/api/data/Android/10/1
        String url = new UrlBuilder("http://gank.io/api/data").sp("Android").sp("10").sp(page).build();
        GetMethod method = new GetMethod(url);
        List<Meizhi> meizhis = method.executeSync(new MeizhiParser<List<Meizhi>>() {
            @Override
            protected void onParse(Response responses, List<Meizhi> meizhis) {
                mPage = page;
            }
        });
        return meizhis;
    }

    @Override
    public List<Meizhi> refresh() throws Exception {
        return load(1);
    }

    @Override
    public List<Meizhi> loadMore() throws Exception {
        return load(mPage + 1);
    }

    @Override
    public boolean hasMore() {
        return true;
    }
}
