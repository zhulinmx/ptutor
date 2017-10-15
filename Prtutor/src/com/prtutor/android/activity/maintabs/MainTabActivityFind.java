package com.prtutor.android.activity.maintabs;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.Next_Search;
import com.google.prtutor.R;
import com.google.prtutor.dao.CourseDAO;
import com.google.prtutor.dao.FavoriteDAO;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.activity.order.Addcomment;
import com.prtutor.android.activity.order.MyOrder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainTabActivityFind extends Fragment {
	private ListView listView;
	ArrayList<HashMap<String, Object>> listData;
	private CourseDAO courseDAO;
	private FavoriteDAO favoriteDAO;
	BaseAdapter adapter;
	String findtop;
	Cursor cursor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_maintabs_find, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		courseDAO = new CourseDAO(getActivity());
		favoriteDAO = new FavoriteDAO(getActivity());
		initView();
		initEvents();
		quaData();
	}

	private void initView() {
		listView = (ListView) getActivity().findViewById(R.id.top_lv_course);
	}

	public void initEvents() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
				int courseid = Integer.parseInt(listData.get(pos).get("courseid").toString());
				Intent intent = new Intent(getActivity(), CourseAction.class);
				intent.putExtra("courseId", courseid);
				startActivity(intent);
			}
		});
	}

	public void quaData() {
		listData = new ArrayList<HashMap<String, Object>>();
		cursor = courseDAO.queryTop();
		int columnsSize = cursor.getColumnCount();
		int j = 1;
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("top", j);
			for (int i = 0; i < columnsSize; i++) {
				map.put("img", cursor.getInt(1));
				map.put("nums", cursor.getInt(2) + "人已报名");
				map.put("tutor", cursor.getString(3));
				map.put("courseName", cursor.getString(0));
				map.put("courseid", cursor.getInt(4));
			}
			listData.add(map);
			j++;
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(getActivity(), listData, R.layout.item_top_course,
				new String[] { "top", "img", "nums", "tutor", "courseName" },
				new int[] { R.id.list_tv_top_num, R.id.list_iv_top, R.id.list_tv_top_enrollment, R.id.list_tv_top_tutor,
						R.id.list_tv_top_course });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
