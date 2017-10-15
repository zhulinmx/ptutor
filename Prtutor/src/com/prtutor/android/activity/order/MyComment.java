package com.prtutor.android.activity.order;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.BaseDialog;
import com.google.prtutor.R;
import com.google.prtutor.dao.CommentDAO;
import com.google.prtutor.dao.OrderDAO;
import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.view.HandyTextView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyComment extends BaseActivity implements OnClickListener {

	private ImageButton or_back;
	private ListView listView;
	private HandyTextView hv_page_title;
	private CommentDAO commentDAO;
	private String stuName;
	ArrayList<HashMap<String, Object>> listData;
	BaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_person);
		commentDAO = new CommentDAO(this);
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		stuName = sp.getString("username", "");
		listData = new ArrayList<HashMap<String, Object>>();
		initViews();
		initComment(); // 初始化order
		initEvents();
	}

	@Override
	protected void initViews() {
		or_back = (ImageButton) findViewById(R.id.or_back);
		listView = (ListView) findViewById(R.id.order_list_lv);
		hv_page_title = (HandyTextView) findViewById(R.id.page_title);
		hv_page_title.setText("我的评论");
	}

	@Override
	protected void initEvents() {
		or_back.setOnClickListener(this);
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
	 * 评论信息初始化
	 */
	private void initComment() {

		Cursor cursor = commentDAO.queryStuComment(stuName);
		int columnsSize = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("createTime", cursor.getString(0));
				map.put("comment", "[  "+cursor.getString(1)+"  ]");
				map.put("courseName", cursor.getString(2));
				map.put("img", cursor.getInt(3));

			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.item_list_order,
				new String[] { "courseName", "comment", "createTime", "img"}, new int[] { R.id.list_course_or,
						R.id.list_order_desc, R.id.list_order_money, R.id.list_order_iv });
		listView.setAdapter(adapter);
	}
}
