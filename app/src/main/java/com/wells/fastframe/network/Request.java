package com.wells.fastframe.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.Future;

public abstract class Request<T> {

	public interface Method {
		int GET = 0;
		int POST = 1;
	}

	private final String mUrl;
	private final int mMethod;
	private Listener<T> mListener;
	protected Future<?> mFuture;

	public Request(int method, String url, Listener<T> listener) {
		mUrl = url;
		mMethod = method;
		mListener = listener;
	}

	public Request(String url, Listener<T> listener) {
		this(Method.GET, url, listener);
	}

	protected abstract Response<T> parseNetworkResponse(NetworkResponse response);

	public String getUrl() {
		return mUrl;
	}

	public int getMethod() {
		return mMethod;
	}

	public int getTimeOutMs() {
		return NetworkConfig.DEFAULT_TIMEOUT_MS;
	}

	public Map<String, String> getParams() throws NetworkError {
		return null;
	}

	public void setFuture(Future<?> future) {
		this.mFuture = future;
	}

	public Map<String, String> getHeaders() throws NetworkError {
		return null;
	}

	public byte[] getBody() throws NetworkError {
		Map<String, String> params = getParams();
		if (null != params && params.size() > 0) {
			return encodeParameters(params, getParamsEncoding());
		}
		return null;
	}

	public static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
		StringBuilder encodedParams = new StringBuilder();
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
				encodedParams.append('=');
				encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
				encodedParams.append('&');
			}
			return encodedParams.toString().getBytes(paramsEncoding);
		} catch (UnsupportedEncodingException uee) {
			throw new RuntimeException("Encoding not supported:" + paramsEncoding, uee);
		}

	}

	public String getParamsEncoding() {
		return NetworkConfig.DEFAULT_CHARESET;
	}

	public void deliverPrepare() {
		if (null != mListener) {
			mListener.onPrepare();
		}
	}

	public void deliverSuccess(T response) {
		if (null != mListener) {
			mListener.onSuccess(response);
		}
	}

	public void deliverError(final NetworkError error) {
		if (null != mListener) {
			mListener.onError(error);
		}
	}

	public void deliverFinish() {
		if (null != mListener) {
			mListener.onFinish();
		}
	}

}
