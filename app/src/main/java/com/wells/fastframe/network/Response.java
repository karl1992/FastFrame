package com.wells.fastframe.network;

public class Response<T> {

	public final T result;
	public final NetworkError error;

	public static <T> Response<T> success(T result) {
		return new Response<T>(result);
	}

	public static <T> Response<T> error(NetworkError error) {
		return new Response<T>(error);
	}

	private Response(T result) {
		this.result = result;
		this.error = null;
	}

	private Response(NetworkError error) {
		this.error = error;
		this.result = null;
	}

	public boolean isSuccess() {
		return error == null;
	}

}
