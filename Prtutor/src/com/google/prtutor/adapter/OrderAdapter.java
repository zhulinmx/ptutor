package com.google.prtutor.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.prtutor.R;
import com.prtutor.android.view.HandyTextView;

public class OrderAdapter extends BaseAdapter implements OnClickListener {

	private static final String TAG = "ContentAdapter";
//	private List<String> mContentList;
	private ArrayList<HashMap<String, Object>> listData;
	private LayoutInflater mInflater;
	private Callback mCallback;

	/**
	 * 自定义接口，用于回调按钮点击事件到Activity
	 */
	public interface Callback {
		public void click(View v);
	}

	public OrderAdapter(Context context, ArrayList<HashMap<String, Object>> list, Callback callback) {
		listData = list;
		mInflater = LayoutInflater.from(context);
		mCallback = callback;
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_list_order, null);
			holder = new ViewHolder();
			holder.list_course_or = (HandyTextView) convertView.findViewById(R.id.list_course_or);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//holder.textView.setText(mContentList.get(position));

		holder.btn_comment.setOnClickListener(this);
		holder.btn_comment.setTag(position);
		return convertView;
	}

	public class ViewHolder {
		public HandyTextView list_course_or;
		public HandyTextView list_order_time;
		public Button btn_comment;
	}

	// 响应按钮点击事件,调用子定义接口，并传入View
	@Override
	public void onClick(View v) {
		mCallback.click(v);
	}
}