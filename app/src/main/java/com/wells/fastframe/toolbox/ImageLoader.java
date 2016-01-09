package com.wells.fastframe.toolbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.wells.fastframe.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader {
	private static Context mContext;
	private static final int MAX_CAPACITY = 20;
	private static ImageLoader instance;
	private int DefaultImageResId = R.drawable.ic_launcher;
	private static String diskCachePath;

	public ImageLoader(Context context) {
		mContext = context;
		diskCachePath =  mContext.getCacheDir().getAbsolutePath() + File.separator;
	}

	public static ImageLoader getInstance(Context context) {
		if (instance == null) {
			instance = new ImageLoader(context);
		}
		return instance;
	};

	@SuppressWarnings("serial")
	private static LinkedHashMap<String, Bitmap> firstCacheMap = new LinkedHashMap<String, Bitmap>(MAX_CAPACITY, 0.75F,
			true) {
		@Override
		protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
			if (this.size() > MAX_CAPACITY) {
				secondCacheMap.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
				diskCache(eldest.getKey(), eldest.getValue());
				return true;
			}
			return false;
		}
	};

	private static ConcurrentHashMap<String, SoftReference<Bitmap>> secondCacheMap = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

	protected static void diskCache(String key, Bitmap value) {
		String fileName = MD5Utils.decode(key);
		String path = diskCachePath + fileName;
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(path);
			value.compress(Bitmap.CompressFormat.JPEG, 100, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addFirstCache(String key, Bitmap value) {
		if (value != null) {
			synchronized (firstCacheMap) {
				firstCacheMap.put(key, value);
			}
		}
	}

	public void loadImage(String key, ImageView imageView) {
		Bitmap bitmap = getFromCache(key);
		if (bitmap != null) {
			cancelDownload(key, imageView);
			imageView.setImageBitmap(bitmap);
		} else {
			downloadTask(key, imageView);
		}
	}

	private void downloadTask(String key, ImageView imageView) {
		imageView.setImageResource(DefaultImageResId);
		AsynImageLoadTask task = new AsynImageLoadTask(imageView);
		task.execute(key);
	}

	private void cancelDownload(String key, ImageView imageView) {
		AsynImageLoadTask task = new AsynImageLoadTask(imageView);
		if (task != null) {
			String downloadKey = task.key;
			if (downloadKey == null || downloadKey.equals(key)) {
				task.cancel(true);
			}
		}

	}

	private Bitmap getFromCache(String key) {
		synchronized (firstCacheMap) {
			Bitmap bitmap = firstCacheMap.get(key);
			if (bitmap != null) {
				firstCacheMap.remove(key);
				firstCacheMap.put(key, bitmap);
				return bitmap;
			}
		}

		SoftReference<Bitmap> soft_bitmap = secondCacheMap.get(key);
		if (soft_bitmap != null) {
			Bitmap bitmap = soft_bitmap.get();
			if (bitmap != null) {
				return bitmap;
			}
		} else {
			secondCacheMap.remove(key);
		}

		Bitmap localBitmap = getFromLocal(key);
		if (localBitmap != null) {
			firstCacheMap.put(key, localBitmap);
			return localBitmap;
		}
		return null;
	}

	private Bitmap getFromLocal(String key) {
		String fileName = MD5Utils.decode(key);
		if (fileName == null) {
			return null;
		}
		String path = diskCachePath + fileName;
		FileInputStream is = null;
		try {
			if (new File(path).exists()) {
				is = new FileInputStream(path);
				return BitmapFactory.decodeStream(is);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void setDefaultImageResId(int defaultImageResId) {
		DefaultImageResId = defaultImageResId;
	}

	class AsynImageLoadTask extends AsyncTask<String, Void, Bitmap> {
		private String key;
		private ImageView imageView;

		public AsynImageLoadTask(ImageView imageView) {
			super();
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			key = params[0];
			return HttpUtils.download(key);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if (isCancelled()) {
				bitmap = null;
			}
			if (bitmap != null) {
				addFirstCache(key, bitmap);
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}

}
