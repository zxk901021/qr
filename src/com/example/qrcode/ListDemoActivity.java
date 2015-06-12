package com.example.qrcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.adpter.ListToListAdapter;
import com.example.adpter.MonthListAdapter.ClickCallback;
import com.example.model.Model;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ListDemoActivity extends Activity {

	private ListView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_demo);
		view = (ListView) findViewById(R.id.demo_month_list);
		List<Model> model = new ArrayList<Model>();
		for (int i = 0; i < 5; i++) {
			Model mod = new Model();
			mod.setYearStr("2015");
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			
			for (int j = 0; j < 4; j++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("month", j+1 +"ÔÂ·Ý");
				map.put("count", j + "");
				list.add(map);
			}
			mod.setData(list);
			model.add(mod);
		}
		ListToListAdapter adapter = new ListToListAdapter(this, model, new ClickCallback() {
			
			@Override
			public void imgClick() {
				Toast.makeText(ListDemoActivity.this, "click", Toast.LENGTH_SHORT).show();
			}
		});
		view.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
