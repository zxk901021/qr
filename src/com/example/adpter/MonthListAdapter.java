package com.example.adpter;

import java.util.List;
import java.util.Map;

import com.example.qrcode.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MonthListAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	private List<Map<String, String>> data;
	private ClickCallback callback;
	
	
	public MonthListAdapter(Context context, List<Map<String, String>> data, ClickCallback callback){
		this.context = context;
		this.data = data;
		this.callback = callback;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MonthViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.month_list_layout, null);
			holder = new MonthViewHolder();
			holder.monthText = (TextView) convertView.findViewById(R.id.month_text);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.back = (ImageView) convertView.findViewById(R.id.back);
			convertView.setTag(holder);
		}else {
			holder = (MonthViewHolder) convertView.getTag();
		}
		holder.monthText.setText(data.get(position).get("month"));
		holder.count.setText(data.get(position).get("count"));
		
		holder.back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (callback != null) {
					callback.imgClick();
				}
			}
		});
		
		return null;
	}
	
	class MonthViewHolder {
		private TextView monthText;
		private TextView count;
		private ImageView back;
	}

	public interface ClickCallback{
		public void imgClick();
	}
	
}
