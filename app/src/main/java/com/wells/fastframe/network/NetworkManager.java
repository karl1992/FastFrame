package com.wells.fastframe.network;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class NetworkManager {
	
	private ThreadPoolExecutor mPool;
	private static NetworkManager instance;

	private NetworkManager() {
		mPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	}

	public static NetworkManager newInstance() {
		if (null == instance) {
			instance = new NetworkManager();
		}
		return instance;
	}

	public void addRequest(Request<?> request) {
		Future<?> future = mPool.submit(new NetworkThread(request));
		request.setFuture(future);
	}

}
