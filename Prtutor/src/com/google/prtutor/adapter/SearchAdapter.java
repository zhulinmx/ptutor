package com.google.prtutor.adapter;

import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.prtutor.R;

public class SearchAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	// public ImageLoader imageLoader; //用来下载图片
	public SearchAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.item_list_course, null);

		TextView coursename_tv = (TextView) vi.findViewById(R.id.listview_course_tv);
		TextView coursetime_tv = (TextView) vi.findViewById(R.id.listview_time_tv);
		TextView courseprice_tv = (TextView) vi.findViewById(R.id.listview_price_tv);
		ImageView courseimg_iv = (ImageView) vi.findViewById(R.id.list_course_iv);
		// 设置ListView的相关值
		HashMap<String, String> course = new HashMap<String, String>();
		course = data.get(position);
		return vi;
	}
}
