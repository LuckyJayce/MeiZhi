package com.shizhefei.meizhi.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhefei.meizhi.R;
import com.shizhefei.mvc.ILoadViewFactory.ILoadView;
import com.shizhefei.view.vary.IVaryViewHelper;
import com.shizhefei.view.vary.VaryViewHelper;

public class LoadView implements ILoadView {
	protected IVaryViewHelper helper;
	protected View switchView;

	private OnClickListener onClickRefreshListener;
	private Context context;
	private LayoutParams customParams;

	public LoadView() {
		super();
	}

	public LoadView(LayoutParams customParams) {
		super();
		this.customParams = customParams;
	}

	@Override
	public void init(View switchView, OnClickListener onClickRefreshListener) {
		this.switchView = switchView;
		this.context = switchView.getContext().getApplicationContext();
		this.onClickRefreshListener = onClickRefreshListener;
		helper = new VaryViewHelper(switchView);
	}

	@Override
	public void restore() {
		helper.restoreView();
	}

	@Override
	public void showLoading() {
		View layout = helper.inflate(R.layout.layout_load_ing);
		helper.showLayout(layout);
	}

	@Override
	public void tipFail(Exception e) {
		Toast.makeText(context, "网络加载失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showFail(Exception e) {
		View layout = helper.inflate(R.layout.layout_load_error);
		layout.setOnClickListener(onClickRefreshListener);
		helper.showLayout(layout);
	}

	@Override
	public void showEmpty() {
		View layout = helper.inflate(R.layout.layout_load_empty);
		layout.setOnClickListener(onClickRefreshListener);
		helper.showLayout(layout);
	}

	public void show(String text, OnClickListener onClickListener) {
		View layout = helper.inflate(R.layout.layout_load_empty);
		TextView empty = (TextView) layout.findViewById(R.id.load_empty_textView);
		empty.setText(text);
		layout.setOnClickListener(onClickListener);
		helper.showLayout(layout);
	}

	public void showLayout(View layout) {
		helper.showLayout(layout);
	}

}
