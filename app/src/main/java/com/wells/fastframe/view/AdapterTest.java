package com.wells.fastframe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wells.fastframe.R;
import com.wells.fastframe.adapter.GeneralAdapter;
import com.wells.fastframe.adapter.ItemViewHandler;
import com.wells.fastframe.base.BaseActivity;
import com.wells.fastframe.ioc.ContentView;

import android.view.View;
import android.widget.ListView;

@ContentView (R.layout.activity_adapter)
public class AdapterTest extends BaseActivity {
	private ListView lv;
	private GeneralAdapter adapter;
	private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();


	@Override
	protected void initData() {
		
		lv = (ListView) findViewById(R.id.lv);
		adapter = new GeneralAdapter(lv, datas, new String[] { "title", "icon" }, new int[] { R.id.tv, R.id.img },
				R.layout.item_adapter_test, new itemHandler());
		lv.setAdapter(adapter);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "张三");
		map.put("icon", R.drawable.ic_launcher);
		datas.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "李四");
		map.put("icon", R.drawable.bg_chart);
		datas.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "王五");
		map.put("icon", R.drawable.ic_launcher);
		datas.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "赵六");
		map.put("icon", R.drawable.bg_chart);
		datas.add(map);
		adapter.notifyDataSetChanged();
		
		
	}
	
	private class itemHandler implements ItemViewHandler{

		@Override
		public void handleView(View convertView, Object obj, final int position) {
			convertView.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					toast("click position is "+position);
				}
			});
			
		}
		
	}
}
