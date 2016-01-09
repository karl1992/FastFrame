package com.wells.fastframe.view;

import java.util.ArrayList;
import java.util.List;

import com.wells.fastframe.R;
import com.wells.fastframe.adapter.GalleryAdapter.OnItemClickLitener;
import com.wells.fastframe.adapter.TestRecyclerAdapter;
import com.wells.fastframe.base.BaseActivity;
import com.wells.fastframe.ioc.ContentView;
import com.wells.fastframe.ioc.ViewInject;
import com.wells.fastframe.toolbox.HorizontalDividerItemDecoration;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

@ContentView(R.layout.test)
public class HomeActivity extends BaseActivity {

	private ProgressDialog mProgressDialog;
	@ViewInject(R.id.recyclerView)
	private RecyclerView mRecyclerView;
	// private GalleryAdapter mAdapter;
	private TestRecyclerAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	@Override
	protected void CreateUI() {
		// setContentView(R.layout.activity_home);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("温馨提示");
		mProgressDialog.setMessage("正在加载中…");

//		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
				.color(Color.WHITE)
				.size(getResources().getDimensionPixelSize(R.dimen.divider))
				.margin(getResources().getDimensionPixelSize(R.dimen.leftmargin),
						getResources().getDimensionPixelSize(R.dimen.rightmargin)).build());

		linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);

		List<String> items = new ArrayList<String>();

		for (int i = 0; i < 30; i++) {
			items.add("测试" + i);
		}

		// 设置适配器
		adapter = new TestRecyclerAdapter(this, items, R.layout.item_adapter_test);
		mRecyclerView.setAdapter(adapter);
		adapter.setOnItemClickLitener(new OnItemClickLitener() {

			@Override
			public void onItemClick(View view, int position) {
				toast("click common recyclerView position is " + position);
			}
		});

		// mAdapter = new GalleryAdapter(this, items);
		// mRecyclerView.setAdapter(mAdapter);
		// mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
		// @Override
		// public void onItemClick(View view, int position) {
		// toast("click recyclerView position is "+position);
		// }
		// });
		mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, items.size() - 1);
	}

	@Override
	protected void initData() {

	}

}
