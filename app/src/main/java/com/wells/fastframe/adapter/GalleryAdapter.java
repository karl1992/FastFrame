package com.wells.fastframe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.wells.fastframe.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

	private OnItemClickLitener mOnItemClickLitener;
	private LayoutInflater mInflater;
	private List<String> mItems;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	private List<Integer> mPos = new ArrayList<Integer>();

	public List<Integer> getmPos() {
		return mPos;
	}

	public void setmPos(Integer position) {
		if (mPos.contains(position)) {
			mPos.remove((Integer) position);
		} else {
			mPos.add(position);
		}
		notifyDataSetChanged();
	}

	public GalleryAdapter(Context context, List<String> items) {
		mInflater = LayoutInflater.from(context);
		mItems = items;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View arg0) {
			super(arg0);
		}

		TextView mTxt;
		RelativeLayout mLayout;
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = mInflater.inflate(R.layout.item_adapter_test, viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(view);
		viewHolder.mTxt = (TextView) view.findViewById(R.id.tv);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
		viewHolder.mTxt.setText(mItems.get(i));
		if (mOnItemClickLitener != null) {
			viewHolder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
				}
			});
		}

	}

	public interface OnItemClickLitener {
		void onItemClick(View view, int position);
	}

}
