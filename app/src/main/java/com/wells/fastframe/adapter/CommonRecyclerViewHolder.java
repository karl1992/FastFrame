package com.wells.fastframe.adapter;

import com.wells.fastframe.toolbox.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {
	
	private SparseArray<View> mViews;
	private View mConvertView;
	private Context mContext;
	
	public CommonRecyclerViewHolder(View view,Context context) {
		super(view);
		this.mViews = new SparseArray<View>();
		this.mContext = context;
		mConvertView = view;
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
	
	public CommonRecyclerViewHolder setText(int viewId, String text) {
		((TextView) getView(viewId)).setText(text);
		return this;
	}

	public CommonRecyclerViewHolder setImageResource(int viewId, int resId) {
		ImageView iv = (ImageView) getView(viewId);
		iv.setImageResource(resId);
		return this;
	}

	public CommonRecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView iv = (ImageView) getView(viewId);
		iv.setImageBitmap(bitmap);
		return this;
	}

	public CommonRecyclerViewHolder setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		view.setVisibility(visibility);
		return this;
	}

	public CommonRecyclerViewHolder setImageURL(int viewId, String url) {
		ImageView view = getView(viewId);
		ImageLoader.getInstance(mContext).loadImage(url, view);
		return this;
	}

}
