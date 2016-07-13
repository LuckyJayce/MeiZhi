package com.shizhefei.meizhi.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
        private final int color;

        public ItemViewHolder(View itemView) {
            super(itemView);
            color = context.getResources().getColor(R.color.text_secondary);
            textView = (TextView) findViewById(R.id.item_gank_textView);
        }

        @Override
        public void setData(Gank gank, int position) {
            SpannableStringBuilder text = new SpannableStringBuilder(gank.desc);
            if (!TextUtils.isEmpty(gank.who)) {
                text.append("  (by:").append(gank.who).append(")");
                text.setSpan(new ForegroundColorSpan(color), gank.desc.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            textView.setText(text);
        }
    }
}
