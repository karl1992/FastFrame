package com.wells.fastframe.network;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringRequest extends Request<String> {
	
	private Map<String, String> mParams;

	public StringRequest(int method, String url, Map<String, String> params, Listener<String> listener) {
		super(method, url, listener);
		mParams = params;
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.mData, response.mChareset);
		} catch (UnsupportedEncodingException uee) {
			parsed = new String(response.mData);
		}
		return Response.success(parsed);
	}

	@Override
	public Map<String, String> getParams() throws NetworkError {
		return null != mParams ? mParams : new HashMap<String, String>();
	}

}
