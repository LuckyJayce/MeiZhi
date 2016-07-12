package com.shizhefei.meizhi.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.utils.ImageUtils;
import com.shizhefei.meizhi.modle.entry.Meizhi;
import com.shizhefei.meizhi.view.RatioImageView;

public class MeizhiAdapter extends ListDataAdapter<Meizhi> {

    @Override
    public AbsItemViewHolder onCreateViewHolderHF(ViewGroup viewGroup, int type) {
        return new ItemViewHolder(inflate(R.layout.item_meizhi, viewGroup));
    }

    public class ItemViewHolder extends AbsItemViewHolder {

        public final RatioImageView imageView;
        public final TextView textView;
        public final View card;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (RatioImageView) findViewById(R.id.item_main_imageView);
            textView = (TextView) findViewById(R.id.item_main_textView);
            card = findViewById(R.id.item_main_card_View);
            imageView.setOriginalSize(50, 50);
        }

        @Override
        public void setData(Meizhi meizhi, int position) {
            int limit = 48;
            String text = meizhi.desc.length() > limit ? meizhi.desc.substring(0, limit) +
                    "..." : meizhi.desc;
            textView.setText(text);
            ImageUtils.display(imageView, meizhi.url);
        }
    }
}