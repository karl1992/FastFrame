package com.wells.fastframe.network;

import java.util.concurrent.Executor;

import android.os.Handler;

public class ExecutorDelivery {

	private final Executor mResponePoster;

	public ExecutorDelivery(final Handler handler) {
		mResponePoster = new Executor() {
			@Override
			public void execute(Runnable command) {
				handler.post(command);
			}
		};
	}

	public ExecutorDelivery(Executor executor) {
		mResponePoster = executor;
	}

	public void postResponse(Request<?> request, Response<?> response) {
		postResponse(request, response, null);

	}

	public void postResponse(Request<?> request, Response<?> response, Runnable runable) {
		mResponePoster.execute(new ResponeDeliveryRunable(request, response, runable));

	}

	public void postError(Request<?> request, NetworkError error) {
		Response<?> response = Response.error(error);
		mResponePoster.execute(new ResponeDeliveryRunable(request, response, null));
	}

	public void postPreExecute(final Request<?> request) {
		mResponePoster.execute(new Runnable() {
			@Override
			public void run() {
				request.deliverPrepare();
			}
		});
	}

	private class ResponeDeliveryRunable implements Runnable {

		@SuppressWarnings("rawtypes")
		private final Request mRequest;
		private final Response<?> mResponse;
		private final Runnable mRunnable;

		public ResponeDeliveryRunable(Request<?> request, Response<?> response, Runnable runnable) {
			mRequest = request;
			mResponse = response;
			mRunnable = runnable;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			if (mResponse.isSuccess()) {
				mRequest.deliverSuccess(mResponse.result);
			} else {
				mRequest.deliverError(mResponse.error);
			}

			if (mRunnable != null) {
				mRunnable.run();
			}
			mRequest.deliverFinish();
		}
	}

}
