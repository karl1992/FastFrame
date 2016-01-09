package com.wells.fastframe.network;

import java.io.IOException;
import java.net.ConnectException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class NetworkThread extends Thread {

	private Request<?> mRequest;
	private ExecutorDelivery delivery;
	private HttpStack mHttpStack;

	public NetworkThread(Request<?> request) {
		this(request, new HurlStack());
	}

	public NetworkThread(Request<?> request, HttpStack httpStack) {
		mRequest = request;
		mHttpStack = httpStack;
		delivery = new ExecutorDelivery(new Handler(Looper.getMainLooper()));
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		try {
			delivery.postPreExecute(mRequest);
			NetworkResponse networkResponse = perfromRequest(mRequest);
			Response<?> response = mRequest.parseNetworkResponse(networkResponse);
			delivery.postResponse(mRequest, response);
		} catch (NetworkError e) {
			delivery.postError(mRequest, e);
		}
	}

	public NetworkResponse perfromRequest(Request<?> request) throws NetworkError {
		while (true) {
			HttpResponse httpResponse = null;
			byte[] responseBytes = null;
			try {
				httpResponse = mHttpStack.performRequest(request);
				StatusLine statusLine = httpResponse.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode < 200 || statusCode > 299) {
					throw new IOException("statusCode greater than 299 or less than 200");
				}
				if (null != httpResponse.getEntity()) {
					responseBytes = entityToBytes(httpResponse.getEntity());
				} else {
					responseBytes = new byte[0];
				}
				return new NetworkResponse(responseBytes);
			} catch (ConnectException e) {
				e.printStackTrace();
				throw new NetworkError("network connection exception");
			} catch (IOException e) {
				e.printStackTrace();
				throw new NetworkError("IO abnormal operation");
			}
		}

	}

	private byte[] entityToBytes(HttpEntity entity) throws IOException, NetworkError {
		byte[] buffer = null;
		try {
			buffer = EntityUtils.toByteArray(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NetworkError("Error occured when entityToBytes");
		} finally {
			try {
				entity.consumeContent();
			} catch (IOException e) {
				throw new NetworkError("Error occured when calling consumingContent");
			}
		}
		return buffer;
	}
}
