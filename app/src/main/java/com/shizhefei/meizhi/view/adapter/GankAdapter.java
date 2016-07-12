package com.shizhefei.meizhi.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.controller.WebViewActivity;
import com.shizhefei.meizhi.modle.entry.Gank;

/**
 * Created by LuckyJayce on 2016/7/12.
 */
public class GankAdapter extends ListDataAdapter<Gank> {
    @Override
    public AbsItemViewHolder onCreateViewHolderHF(ViewGroup viewGroup, int type) {
        return new ItemViewHolder(inflate(R.layout.item_gank, viewGroup));
    }

    @Override
    protected boolean onItemClick(RecyclerView.ViewHolder vh, int position) {
        Gank gank = getData().get(position);
        WebViewActivity.startWeb(vh.itemView.getContext(), gank.desc, gank.url);
        return true;
    }

    private class ItemViewHolder extends AbsItemViewHolder {

        private final TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) findViewById(R.id.item_gank_textView);
        }

        @Override
        public void setData(Gank gank, int position) {
            textView.setText(gank.desc);
        }
    }
}
