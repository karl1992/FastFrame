package com.wells.fastframe.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	protected int layoutId;

	public List<T> getDatas() {
		return mDatas;
	}

	public void setDatas(List<T> datas) {
		if (mDatas != null) {
			mDatas.clear();
		}
		mDatas = datas;
		notifyDataSetChanged();
	}

	public CommonAdapter(Context context, List<T> datas, int layoutId) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
		convert(holder, getItem(position));
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder, T t);

}
