package com.wells.fastframe.adapter;

import com.wells.fastframe.toolbox.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;

	public int getPosition() {
		return mPosition;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
		this.context = context;
	}

	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}

	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;

	}

	public ViewHolder setText(int viewId, String text) {
		((TextView) getView(viewId)).setText(text);
		return this;
	}

	public ViewHolder setImageResource(int viewId, int resId) {
		ImageView iv = (ImageView) getView(viewId);
		iv.setImageResource(resId);
		return this;
	}

	public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView iv = (ImageView) getView(viewId);
		iv.setImageBitmap(bitmap);
		return this;
	}

	public ViewHolder setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		view.setVisibility(visibility);
		return this;
	}

	public ViewHolder setImageURL(int viewId, String url) {
		ImageView view = getView(viewId);
		ImageLoader.getInstance(context).loadImage(url, view);
		return this;
	}

	public ViewHolder setImageURL(int viewId, String url, boolean isUseCache) {	
		ImageView view = getView(viewId);
		ImageLoader.getInstance(context).loadImage(url, view);
		return this;
	}

}