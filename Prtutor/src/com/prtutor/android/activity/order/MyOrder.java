package com.prtutor.android.activity.order;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.BaseDialog;
import com.google.prtutor.Next_Search;
import com.google.prtutor.R;
import com.google.prtutor.SearchActivity;
import com.google.prtutor.adapter.OrderAdapter;
import com.google.prtutor.dao.OrderDAO;
import com.google.prtutor.model.Tb_order;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.activity.maintabs.MainTabActivityPerson;
import com.prtutor.android.view.HandyTextView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.google.prtutor.adapter.OrderAdapter;
import com.google.prtutor.adapter.OrderAdapter.Callback;

public class MyOrder extends BaseActivity implements OnClickListener {

	private ImageButton or_back;
	private ListView listView;
	private BaseDialog mBackDialog;
	private OrderDAO orderDAO;
	private String stuName;
	private int orserid,pos,courseid;
	ArrayList<HashMap<String, Object>> listData;
	BaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_person);
		orderDAO = new OrderDAO(this);
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		stuName = sp.getString("username", "");
		listData = new ArrayList<HashMap<String, Object>>();
		initViews();
		initOrder(); // 初始化order
		initEvents();
	}

	@Override
	protected void initViews() {
		or_back = (ImageButton) findViewById(R.id.or_back);
		listView = (ListView) findViewById(R.id.order_list_lv);
	}

	@Override
	protected void initEvents() {
		or_back.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
				int courseid = Integer.parseInt(listData.get(pos).get("courseid").toString());
				int	oderid = Integer.parseInt(listData.get(pos).get("orserid").toString());
				Intent intent = new Intent(MyOrder.this, Addcomment.class);
				intent.putExtra("stuName", stuName);
				intent.putExtra("courseid", courseid);
				intent.putExtra("oderid", oderid);
				startActivity(intent);
			}

		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				pos = position;
				orserid = Integer.parseInt(listData.get(position).get("orserid").toString());
				courseid = Integer.parseInt(listData.get(position).get("courseid").toString());
				mBackDialog = BaseDialog.getDialog(MyOrder.this, "提示", "确认要取消订单吗？", "确认",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								orderDAO.deleteOrder(orserid,courseid);
								listData.remove(pos);
								adapter.notifyDataSetChanged();
								dialog.dismiss();

							}
						}, "我再考虑一下", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
				mBackDialog.show();
				return true;
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
	 * 订单信息初始化
	 */
	private void initOrder() {

		Cursor cursor = orderDAO.queryOrder(stuName);
		int columnsSize = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("courseName", cursor.getString(4));
				map.put("odstage", cursor.getString(2));
				map.put("createTime", cursor.getString(3));
				map.put("orserid", cursor.getInt(0));
				map.put("courseid", cursor.getInt(1));
				map.put("img", cursor.getInt(9));
				map.put("price", "￥"+cursor.getFloat(10));
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.item_list_order,
				new String[] { "courseName", "odstage", "createTime", "img", "price"},
				new int[] { R.id.list_course_or, R.id.list_order_desc, R.id.list_order_money, R.id.list_order_iv,R.id.list_order_time });
		listView.setAdapter(adapter);
	}

	
}
