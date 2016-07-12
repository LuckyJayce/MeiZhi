package com.shizhefei.meizhi.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.modle.entry.Gank;
import com.shizhefei.mvc.IDataAdapter;
import com.shizhefei.utils.ArrayListMap;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.List;

public class DetailPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter implements IDataAdapter<ArrayListMap<String, List<Gank>>> {
    private ArrayListMap<String, List<Gank>> data = new ArrayListMap<>();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(data.keyAt(position));
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            Context context = container.getContext();
            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new GankAdapter());
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            convertView = recyclerView;
        }
        RecyclerView recyclerView = (RecyclerView) convertView;
        GankAdapter adapter = (GankAdapter) recyclerView.getAdapter();
        List<Gank> ganks = data.valueAt(position);
        adapter.notifyDataChanged(ganks, true);
        return convertView;
    }

    @Override
    public void notifyDataChanged(ArrayListMap<String, List<Gank>> stringListArrayListMap, boolean isRefresh) {
        data.putAll(stringListArrayListMap);
        notifyDataSetChanged();
    }

    @Override
    public ArrayListMap<String, List<Gank>> getData() {
        return data;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}