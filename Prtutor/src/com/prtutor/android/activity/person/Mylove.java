package com.prtutor.android.activity.person;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.FavoriteDAO;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.activity.teacher.TeacherAction;
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

public class Mylove extends BaseActivity implements OnClickListener {
	private ImageButton or_back;
	private ListView listView;
	private FavoriteDAO favoriteDAO;
	private HandyTextView tvTitle;
	ArrayList<HashMap<String, Object>> listData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_person);
		favoriteDAO = new FavoriteDAO(this);
		initViews();
		initLove();
		initEvents();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		or_back = (ImageButton) findViewById(R.id.or_back);
		listView = (ListView) findViewById(R.id.order_list_lv);
		tvTitle = (HandyTextView) findViewById(R.id.page_title);
		tvTitle.setText("我的关注");
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

		or_back.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
				int trId = Integer.parseInt(listData.get(pos).get("id").toString());
				Intent intent = new Intent(Mylove.this, TeacherAction.class);
				intent.putExtra("trId", trId);
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
	private void initLove() {
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String username = sp.getString("username", "");
		BaseAdapter adapter;
		Cursor cursor = favoriteDAO.displayLove(username);
		int columnsSize = cursor.getColumnCount();// 关注数目
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("Name", cursor.getString(0));
				map.put("id", cursor.getInt(1));
				map.put("img", cursor.getInt(2));
				map.put("qua", cursor.getString(3));
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.item_guanzhu_tutor, new String[] { "Name", "qua", "img"},
				new int[] { R.id.list_guanzhu_iv_name, R.id.list_guanzhu_iv_qua, R.id.list_guanzhu_rv_img });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

}
