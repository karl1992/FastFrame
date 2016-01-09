package com.wells.fastframe.network;

public abstract class Listener<T> {

	public void onPrepare() {};

	public void onFinish() {};

	public void onError(NetworkError error) {};

	public abstract void onSuccess(T response);
}
