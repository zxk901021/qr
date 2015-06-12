package com.example.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adpter.MonthListAdapter.ClickCallback;
import com.example.model.Model;
import com.example.qrcode.R;

import java.util.List;

public class ListToListAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private List<Model> model;
	private ClickCallback callback;

	public ListToListAdapter(Context context, List<Model> model, ClickCallback callback){
		this.context = context;
		this.model = model;
		this.callback = callback;
		inflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public int getCount() {
		return model.size();
	}

	@Override
	public Object getItem(int position) {
		return model.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_to_list_layout, null);
			holder = new ListViewHolder();
			holder.year = (TextView) convertView.findViewById(R.id.year_text);
			holder.monthList = (ListView) convertView.findViewById(R.id.month_list_text);
			convertView.setTag(holder);
		}else {
			holder = (ListViewHolder) convertView.getTag();
		}
		holder.year.setText(model.get(position).getYearStr());
		holder.monthList.setAdapter(new MonthListAdapter(context, model.get(position).getData(), callback));
		
		return null;
	}
	
	class ListViewHolder {
		private TextView year;
		private ListView monthList;
		
	}

}
