package com.wells.fastframe.adapter;

import java.util.List;

import com.wells.fastframe.adapter.GalleryAdapter.OnItemClickLitener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	protected int layoutId;
	private OnItemClickLitener mOnItemClickLitener;

	public CommonRecyclerAdapter(Context context, List<T> datas, int layoutId) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		this.layoutId = layoutId;
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	@Override
	public void onBindViewHolder(final CommonRecyclerViewHolder viewHolder, final int position) {
		convert(viewHolder, position, mDatas.get(position));
		if (mOnItemClickLitener != null) {
			viewHolder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
				}
			});
		}
	}

	@Override
	public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		return new CommonRecyclerViewHolder(mInflater.inflate(layoutId, parent, false),mContext);
	}

	public abstract void convert(CommonRecyclerViewHolder viewHolder, int i, T t);
	
	
	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}


//	public abstract CommonRecyclerViewHolder createViewHolder(View view);

}
