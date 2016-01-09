package com.wells.fastframe.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class HurlStack implements HttpStack {

	@Override
	public HttpResponse performRequest(Request<?> request) throws IOException, NetworkError {

		URL parsedUrl = new URL(request.getUrl());
		HttpURLConnection connection = openConnection(parsedUrl, request);

		if (null != request.getHeaders() && request.getHeaders().size() > 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll(request.getHeaders());
			for (String headerName : map.keySet()) {
				connection.addRequestProperty(headerName, map.get(headerName));
			}
		}

		setParamsForRequest(connection, request);
		int requestCode = connection.getResponseCode();

		if (requestCode == -1) {
			throw new NetworkError("Could not retrieve response code from HttpURLConnection.");

		}

		StatusLine responseStatus = new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1),
				connection.getResponseCode(), connection.getResponseMessage());
		BasicHttpResponse response = new BasicHttpResponse(responseStatus);
		response.setEntity(entityFormConnection(connection));

		for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
			if (null != header.getKey()) {
				Header h = new BasicHeader(header.getKey(), header.getValue().get(0));
				response.addHeader(h);
			}
		}
		return response;
	}

	private static HttpEntity entityFormConnection(HttpURLConnection connection) {
		BasicHttpEntity entity = new BasicHttpEntity();
		InputStream inputStream;

		try {
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			inputStream = connection.getErrorStream();
		}

		entity.setContent(inputStream);
		entity.setContentLength(connection.getContentLength());
		entity.setContentEncoding(connection.getContentEncoding());
		entity.setContentType(connection.getContentType());
		return entity;

	}

	private HttpURLConnection openConnection(URL url, Request<?> request) throws IOException {
		HttpURLConnection connection = createConnection(url);
		int timeoutMs = request.getTimeOutMs();
		connection.setConnectTimeout(timeoutMs);
		connection.setReadTimeout(timeoutMs);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		return connection;
	}

	private HttpURLConnection createConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}

	private static void setParamsForRequest(HttpURLConnection connection, Request<?> request) throws IOException,
			NetworkError {
		switch (request.getMethod()) {
		case Request.Method.GET:
			connection.setRequestMethod("GET");
			break;
		case Request.Method.POST:
			connection.setRequestMethod("POST");
			break;
		default:
			throw new IllegalStateException("Unknow method type");
		}
	}

}
