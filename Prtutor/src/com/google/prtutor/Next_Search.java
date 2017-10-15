package com.google.prtutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.prtutor.dao.CourseDAO;
import com.prtutor.android.activity.course.CourseAction;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow.OnDismissListener;

public class Next_Search extends Activity implements OnClickListener {

	private ListView listView, popListView;
	private ProgressBar progressBar;
	private List<Map<String, String>> menuData1, menuData2, menuData3;
	private PopupWindow popMenu;
	private SimpleAdapter menuAdapter1, menuAdapter2, menuAdapter3;

	private LinearLayout product, sort, activity;
	private TextView productTv, sortTv, activityTv;
	private EditText titleTv;
	private ImageButton cartIv;
	private int green, grey;

	private static String currentProduct = "", currentSort = "", currentActivity = "";// 定义为静态成员
	private int menuIndex = 0;

	private Intent intent;
	private CourseDAO courseDAO;
	String search_content;
	BaseAdapter adapter;
	Cursor cursor;
	// 存储数据的数组列表
	ArrayList<HashMap<String, Object>> listData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		courseDAO = new CourseDAO(this);
		findView();
		initPopMenu();
		intent = getIntent();
		if (intent.getStringExtra("search") != null) {
			search_content = intent.getStringExtra("search");
			titleTv.setText(search_content);
			searchRule(search_content, "", "", "全部");
		} // 搜索的内容显示在search框中
		if (intent.getStringExtra("title") != null) {
			currentProduct = intent.getStringExtra("title");
			searchRule("", intent.getStringExtra("title"), "", "全部");
			productTv.setText(intent.getStringExtra("title"));
		}
	}

	private void initMenuData() {
		menuData1 = new ArrayList<Map<String, String>>();
		String[] menuStr1 = new String[] { "全部", "幼小", "初中", "高中", "艺术", "生活", "职业", "语言", "其他" };
		Map<String, String> map1;
		for (int i = 0, len = menuStr1.length; i < len; ++i) {
			map1 = new HashMap<String, String>();
			map1.put("name", menuStr1[i]);
			menuData1.add(map1);
		}

		menuData2 = new ArrayList<Map<String, String>>();
		String[] menuStr2 = new String[] { "综合排序", "评分最高" };
		Map<String, String> map2;
		for (int i = 0, len = menuStr2.length; i < len; ++i) {
			map2 = new HashMap<String, String>();
			map2.put("name", menuStr2[i]);
			menuData2.add(map2);
		}

		menuData3 = new ArrayList<Map<String, String>>();
		String[] menuStr3 = new String[] { "全部", "一对一", "线下班课" };
		Map<String, String> map3;
		for (int i = 0, len = menuStr3.length; i < len; ++i) {
			map3 = new HashMap<String, String>();
			map3.put("name", menuStr3[i]);
			menuData3.add(map3);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.supplier_list_product:
			productTv.setTextColor(getResources().getColor(R.color.orange));
			popListView.setAdapter(menuAdapter1);
			popMenu.showAsDropDown(product, 0, 2);
			menuIndex = 0;
			break;
		case R.id.supplier_list_sort:
			sortTv.setTextColor(getResources().getColor(R.color.orange));
			popListView.setAdapter(menuAdapter2);
			popMenu.showAsDropDown(product, 0, 2);
			menuIndex = 1;
			break;
		case R.id.supplier_list_activity:
			activityTv.setTextColor(getResources().getColor(R.color.orange));
			popListView.setAdapter(menuAdapter3);
			popMenu.showAsDropDown(product, 0, 2);
			menuIndex = 2;
			break;
		case R.id.search_result:
			finish();
			break;
		case R.id.back_to_search:
			finish();
			break;
		}
	}

	protected void findView() {

		listView = (ListView) findViewById(R.id.supplier_list_lv);
		product = (LinearLayout) findViewById(R.id.supplier_list_product);
		sort = (LinearLayout) findViewById(R.id.supplier_list_sort);
		activity = (LinearLayout) findViewById(R.id.supplier_list_activity);
		productTv = (TextView) findViewById(R.id.supplier_list_product_tv);
		sortTv = (TextView) findViewById(R.id.supplier_list_sort_tv);
		activityTv = (TextView) findViewById(R.id.supplier_list_activity_tv);
		titleTv = (EditText) findViewById(R.id.search_result);
		cartIv = (ImageButton) findViewById(R.id.back_to_search);
		progressBar = (ProgressBar) findViewById(R.id.progress);

		product.setOnClickListener(this);
		sort.setOnClickListener(this);
		activity.setOnClickListener(this);
		cartIv.setOnClickListener(this);
		titleTv.setOnClickListener(this);
	}

	private void initPopMenu() {
		initMenuData();
		View contentView = View.inflate(this, R.layout.popwin_supplier_list, null);
		popMenu = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		popMenu.setOutsideTouchable(true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setFocusable(true);
		popMenu.setAnimationStyle(R.style.popwin_anim_style);
		popMenu.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				productTv.setTextColor(Color.parseColor("#5a5959"));
				sortTv.setTextColor(Color.parseColor("#5a5959"));
				activityTv.setTextColor(Color.parseColor("#5a5959"));
			}
		});

		popListView = (ListView) contentView.findViewById(R.id.popwin_supplier_list_lv);
		contentView.findViewById(R.id.popwin_supplier_list_bottom).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				popMenu.dismiss();
			}
		});
		menuAdapter1 = new SimpleAdapter(this, menuData1, R.layout.item_listview_popwin, new String[] { "name" },
				new int[] { R.id.listview_popwind_tv });
		menuAdapter2 = new SimpleAdapter(this, menuData2, R.layout.item_listview_popwin, new String[] { "name" },
				new int[] { R.id.listview_popwind_tv });
		menuAdapter3 = new SimpleAdapter(this, menuData3, R.layout.item_listview_popwin, new String[] { "name" },
				new int[] { R.id.listview_popwind_tv });

		popListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				popMenu.dismiss();
				if (menuIndex == 0) {
					currentProduct = menuData1.get(pos).get("name");
					productTv.setText(currentProduct);
				} else if (menuIndex == 1) {
					currentSort = menuData2.get(pos).get("name");
					sortTv.setText(currentSort);
				} else {
					currentActivity = menuData3.get(pos).get("name");
					activityTv.setText(currentActivity);
				}
				searchRule(search_content, currentProduct, currentSort, currentActivity);
			}
		});
	}

	private void courseItem() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				int courseid = Integer.parseInt(listData.get(pos).get("_id").toString());
				Intent intent = new Intent(Next_Search.this, CourseAction.class);
				intent.putExtra("courseId", courseid);
				startActivity(intent);
			}
		});
	}

	public void searchRule(String key, String rule1, String rule2, String rule3) {
		if (rule3 == "全部") {
			rule3 = "";
		}
		if (rule1 == "全部") {
			rule1 = "";
		}
		Toast.makeText(Next_Search.this, rule1 + rule2 + rule3, Toast.LENGTH_SHORT).show();
		cursor = courseDAO.queryCourseRule(key, rule1, rule2, rule3);
		int columnsSize = cursor.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("courseName", cursor.getString(2));
				map.put("start_day", "开课时间:" + cursor.getString(3));
				map.put("price", "￥" + cursor.getFloat(4));
				map.put("_id", cursor.getInt(0));
				map.put("img", cursor.getInt(6));
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.item_list_course,
				new String[] { "courseName", "start_day", "price", "img" }, new int[] { R.id.listview_course_tv,
						R.id.listview_time_tv, R.id.listview_price_tv, R.id.list_course_iv });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		courseItem();
	}
}
