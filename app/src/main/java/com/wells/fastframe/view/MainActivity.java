package com.wells.fastframe.view;

import com.wells.fastframe.R;
import com.wells.fastframe.base.BaseActivity;
import com.wells.fastframe.ioc.ContentView;
import com.wells.fastframe.ioc.OnClick;
import com.wells.fastframe.presenter.MainPresenter;

import android.content.Context;
import android.view.View;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainView {
	
	private Context mContext;
	private MainPresenter presenter;	
	
	@Override
	protected void initData() {
		mContext = MainActivity.this;
		presenter = new MainPresenter(mContext, this);
	}
	
	@OnClick({ R.id.test_btn })
	public void toast(View btn) {
		presenter.testGet();
	}

	@Override
	public String getUrl() {
		return "http://www.baidu.com/";
	}

}
