package com.wells.fastframe.adapter;

import java.util.List;

import com.wells.fastframe.R;

import android.content.Context;

public class TestRecyclerAdapter extends CommonRecyclerAdapter<String> {

	public TestRecyclerAdapter(Context context, List<String> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(CommonRecyclerViewHolder viewHolder, int i, String t) {
		viewHolder.setText(R.id.tv, t);
	}


}
