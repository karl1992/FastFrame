package com.wells.fastframe.adapter;

import java.util.List;
import java.util.Map;

import com.wells.fastframe.toolbox.ImageLoader;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GeneralAdapter extends BaseAdapter {
	private int layoutId;
	private String[] from;
	private int[] to;
	@SuppressWarnings("unused")
	private View view;
	private ItemViewHandler itemViewHandler;
	private List<?> datas;
	private Context context;
	private ImageLoader mImageLoader;
	private int defaultImgId;

	public GeneralAdapter(View view, List<?> datas, String[] from, int[] to, int layoutId,
			ItemViewHandler itemViewHandler) {
		super();
		this.layoutId = layoutId;
		this.from = from;
		this.to = to;
		this.view = view;
		this.itemViewHandler = itemViewHandler;
		this.datas = datas;
		this.context = view.getContext();
		mImageLoader = ImageLoader.getInstance(context);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
			viewHolder = new ViewHolder();
			if (to != null && to.length > 0) {
				viewHolder.resultViews = new View[to.length];
				for (int i = 0; i < to.length; i++) {
					viewHolder.resultViews[i] = convertView.findViewById(to[i]);
				}
			}
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		buildViewContext(position, convertView, viewHolder);
		return convertView;
	}

	private void buildViewContext(int position, View convertView, ViewHolder viewHolder) {
		try {
			if (datas != null && position < datas.size()) {
				if (to != null && to.length > 0 && from != null && from.length > 0) {
					for (int i = 0; i < to.length; i++) {
						View view = viewHolder.resultViews[i];
						if (view != null) {
							Object obj = getFieldValue(getItem(position), from[i]);
							setViewValue(view, obj, from[i], position);
						}
					}
				}
				if (itemViewHandler != null) {
					itemViewHandler.handleView(convertView, getItem(position), position);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setViewValue(View view, final Object obj, final String key, final int position) {
		if (view instanceof TextView && obj == null) {
		} else if (view instanceof TextView && obj instanceof String) {
			((TextView) view).setText(Html.fromHtml((String) obj));
		} else if (view instanceof ImageView) {
			final ImageView tempImage = (ImageView) view;
			tempImage.setTag(key + position);
			loadImage(obj, key, position, tempImage);
		}
	}

	private void loadImage(Object obj, String key, int position, final ImageView tempImage) {
		if (obj != null) {
			if (obj instanceof Integer || obj.getClass() == int.class) {
				tempImage.setImageResource((Integer) obj);
			} else if (obj instanceof String) {
				String url = obj.toString();
				final String tagKey = key + url + position;
				tempImage.setTag(tagKey);
				mImageLoader.loadImage(url, tempImage);
			}
		} else {
			if (defaultImgId == 0) {
				tempImage.setVisibility(View.GONE);
			} else {
				tempImage.setImageResource(defaultImgId);
				tempImage.setVisibility(View.VISIBLE);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Object getFieldValue(Object obj, String key) {
		if (obj != null) {
			if (obj instanceof Map<?, ?>) {
				return ((Map<String, Object>) obj).get(key);
			} else if (obj instanceof String) {
				return obj;
			}
		}
		return null;
	}

	public class ViewHolder {
		public View resultViews[];
	}

	public int getDefaultImgId() {
		return defaultImgId;
	}

	public void setDefaultImgId(int defaultImgId) {
		this.defaultImgId = defaultImgId;
		mImageLoader.setDefaultImageResId(defaultImgId);
	}

}
