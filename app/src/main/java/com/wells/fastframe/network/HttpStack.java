package com.wells.fastframe.network;

import java.io.IOException;

import org.apache.http.HttpResponse;

public interface HttpStack {

	public HttpResponse performRequest(Request<?> request) throws IOException, NetworkError;

}
