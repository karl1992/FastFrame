package com.wells.fastframe.toolbox;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.wells.fastframe.network.Listener;
import com.wells.fastframe.network.NetworkError;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

public class HttpUtils {

	private static final int TIMEOUTMS = 10 * 1000;

	public static Bitmap download(String imageUrl) {
		try {
			URL parsedUrl = new URL(imageUrl);
			HttpURLConnection connection = openConnection(parsedUrl);
			connection.setRequestMethod("GET");
			int requestCode = connection.getResponseCode();
			if (requestCode == -1) {
				throw new IOException("Could not retrieve response code from HttpURLConnection.");
			}

			if (connection.getInputStream() != null && requestCode == HttpStatus.SC_OK) {
				return BitmapFactory.decodeStream(connection.getInputStream());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static HttpURLConnection createConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}

	private static HttpURLConnection openConnection(URL url) throws IOException {
		HttpURLConnection connection = createConnection(url);
		connection.setConnectTimeout(TIMEOUTMS);
		connection.setReadTimeout(TIMEOUTMS);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		return connection;
	}

	public static void uploadFile(String url, String filePath, final Listener<String> listener) {
		newHanderLoop(listener, new Runnable() {
			@Override
			public void run() {
				listener.onPrepare();
			}
		});
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		MultipartEntity multipartEntity = new MultipartEntity();
		File file = new File(filePath);
		multipartEntity.addPart("file", new FileBody(file));
		try {
			httpPost.setEntity(multipartEntity);
			final HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				final String result = EntityUtils.toString(entity);
				newHanderLoop(listener, new Runnable() {
					@Override
					public void run() {
						listener.onSuccess(result);
					}
				});
			}

		} catch (final Exception e) {
			newHanderLoop(listener, new Runnable() {
				@Override
				public void run() {
					listener.onError(new NetworkError(e.getMessage()));
				}
			});
		} finally {
			newHanderLoop(listener, new Runnable() {
				@Override
				public void run() {
					listener.onFinish();
				}
			});
		}
	}

	private static void newHanderLoop(Listener<?> listener, Runnable run) {
		if (listener != null) {
			new Handler(Looper.getMainLooper()).post(run);
		}
	}

}
