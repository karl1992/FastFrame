package com.wells.fastframe.presenter;

import android.content.Context;

import com.wells.fastframe.network.Listener;
import com.wells.fastframe.network.NetworkError;
import com.wells.fastframe.network.NetworkManager;
import com.wells.fastframe.network.StringRequest;
import com.wells.fastframe.network.Request.Method;
import com.wells.fastframe.toolbox.T;
import com.wells.fastframe.view.IMainView;

public class MainPresenter {
	private Context mContext;
	private IMainView IView;
	
	public MainPresenter(Context context,IMainView view){
		mContext = context;
		IView = view;
	}
	
	public void testGet(){
		String url = IView.getUrl();
		StringRequest request = new StringRequest(Method.GET, url, null, new Listener<String>() {
			@Override
			public void onPrepare() {
				T.show(mContext, "onPrepare");
			}
			
			@Override
			public void onSuccess(String response) {
				T.show(mContext, "onSuccess->"+response);
			}
			
			
			@Override
			public void onError(NetworkError error) {
				T.show(mContext, "onError->"+error.getMessage());
			}
			
			@Override
			public void onFinish() {
				T.show(mContext, "onFinish");
			}
			
			
		});
		NetworkManager.newInstance().addRequest(request);
	}
	
	

}
