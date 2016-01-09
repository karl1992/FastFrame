package com.wells.fastframe.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wells.fastframe.ioc.InjectUtils;
import com.wells.fastframe.toolbox.T;

public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		CreateUI();
		initData();
	}

	private void init() {
		InjectUtils.inject(this);
	}

	protected void CreateUI() {

	};

	protected abstract void initData();

	protected void toast(CharSequence cs) {
		T.show(this, cs);
	}

	protected void jumpAct(Class<?> clazz) {
		startActivity(new Intent(this, clazz));
	}

	protected String getTxt(int resId) {
		TextView tv = (TextView) this.findViewById(resId);
		return null == tv ? null : tv.getText().toString();
	}

	protected void setTxt(int resId, String msg) {
		TextView tv = (TextView) this.findViewById(resId);
		tv.setText(msg);
	}

}
