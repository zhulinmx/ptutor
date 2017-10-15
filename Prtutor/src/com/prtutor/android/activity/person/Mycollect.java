package com.prtutor.android.activity.person;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.adapter.OrderAdapter.Callback;
import com.google.prtutor.dao.FavoriteDAO;
import com.google.prtutor.dao.OrderDAO;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.activity.order.Addcomment;
import com.prtutor.android.activity.order.MyOrder;
import com.prtutor.android.view.HandyTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Mycollect extends BaseActivity implements OnClickListener {

	private ImageButton or_back;
	private ListView listView;
	private FavoriteDAO favoriteDAO;
	private HandyTextView tvTitle;
	ArrayList<HashMap<String, Object>> listData;
	BaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_person);
		favoriteDAO = new FavoriteDAO(this);
		initViews();
		initCollect();
		initEvents();
	}

	@Override
	protected void initViews() {
		or_back = (ImageButton) findViewById(R.id.or_back);
		listView = (ListView) findViewById(R.id.order_list_lv);
		tvTitle = (HandyTextView) findViewById(R.id.page_title);
		tvTitle.setText("收藏列表");
	}

	@Override
	protected void initEvents() {
		or_back.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
				int courseid = Integer.parseInt(listData.get(pos).get("courseid").toString());
				Intent intent = new Intent(Mycollect.this, CourseAction.class);
				intent.putExtra("courseId", courseid);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.or_back:
			startActivity(MainTabActivity.class);
			break;
		}
	}

	/**
	 * 收藏信息初始化
	 */
	private void initCollect() {
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String username = sp.getString("username", "");
		Cursor cursor = favoriteDAO.displayFavorite(username);
		int columnsSize = cursor.getColumnCount();// 收藏数目
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("courseName", cursor.getString(0));
				map.put("desc", cursor.getString(1));
				map.put("start_day", cursor.getString(2));
				map.put("img", cursor.getInt(3));
				map.put("price", "￥" + cursor.getFloat(4));
				map.put("courseid", cursor.getInt(5));
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.item_list_order,
				new String[] { "courseName", "desc", "start_day", "img", "price" }, new int[] { R.id.list_course_or,
						R.id.list_order_desc, R.id.list_order_time, R.id.list_order_iv, R.id.list_order_money });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

}
