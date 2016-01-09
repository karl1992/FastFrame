package com.wells.fastframe.network;

import java.io.Serializable;

import org.apache.http.HttpStatus;

@SuppressWarnings("serial")
public class NetworkResponse implements Serializable {

	public final int mStatusCode;
	public final byte[] mData;
	public final String mChareset;

	public NetworkResponse(int statusCode, byte[] data, String chareset) {
		mStatusCode = statusCode;
		mData = data;
		mChareset = chareset;
	}

	public NetworkResponse(byte[] data) {
		this(HttpStatus.SC_OK, data, NetworkConfig.DEFAULT_CHARESET);
	}
}
