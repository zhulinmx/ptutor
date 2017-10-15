package com.prtutor.android.activity.maintabs;

import com.google.prtutor.R;
import com.google.prtutor.dao.OrderDAO;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.prtutor.android.view.CalendarView;
import com.prtutor.android.view.HandyTextView;

public class MainTabActivityCourse extends Fragment implements View.OnClickListener {

	private TextView mTextSelectMonth;
	private ImageButton mLastMonthView;
	private ImageButton mNextMonthView;
	private CalendarView mCalendarView;
	private ListView listView;

	private List<String> mDatas;
	private OrderDAO orderDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_maintabs_couse, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initCourseArrange();
		// 设置已选日期
		// mCalendarView.setSelectedDates(mDatas);
		// 设置不可以被点击
		// mCalendarView.setClickable(false);

		// 设置点击事件
		mCalendarView.setOnClickDate(new CalendarView.OnClickListener() {
			@Override
			public void onClickDateListener(int year, int month, int day) {
				// Toast.makeText(getApplication(), year + "年" + month + "月" +
				// day + "天", Toast.LENGTH_SHORT).show();

				// 获取已选择日期
				List<String> dates = mCalendarView.getSelectedDates();
				for (String date : dates) {
					Log.e("test", "date: " + date);
				}
			}
		});

		mTextSelectMonth.setText(mCalendarView.getDate());
		initEvents();
	}

	private void initView() {
		mTextSelectMonth = (TextView) getActivity().findViewById(R.id.txt_select_month);
		mLastMonthView = (ImageButton) getActivity().findViewById(R.id.img_select_last_month);
		mNextMonthView = (ImageButton) getActivity().findViewById(R.id.img_select_next_month);
		mCalendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
		listView = (ListView) getActivity().findViewById(R.id.calendar_list_lv);

	}

	public void initEvents() {
		mLastMonthView.setOnClickListener(this);
		mNextMonthView.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_select_last_month:
			mCalendarView.setLastMonth();
			mTextSelectMonth.setText(mCalendarView.getDate());
			break;
		case R.id.img_select_next_month:
			mCalendarView.setNextMonth();
			mTextSelectMonth.setText(mCalendarView.getDate());
			break;
		}
	}

	public void initCourseArrange() {
		// 根据课程开始日期、课程节数、间隔天数得到课程安排
		BaseAdapter adapter;
		ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> listViewData = new ArrayList<HashMap<String, Object>>();
		mDatas = new ArrayList<>();
		int daything;
		SharedPreferences sp = getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE);
		String username = sp.getString("username", "").toString();
		orderDAO = new OrderDAO(getActivity());
		Cursor cursorData = orderDAO.queryOrder(username);
		int columnsSize = cursorData.getColumnCount();
		while (cursorData.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("start_day", cursorData.getString(8));
				map.put("time", cursorData.getString(12));
				map.put("name", cursorData.getString(4));
				map.put("total", cursorData.getInt(6));
				map.put("betday", cursorData.getInt(7));
			}
			listData.add(map);
		}
		for (int i = 0; i < listData.size(); i++) {
			String datelist = listData.get(i).get("start_day").toString();
			String start_day = datelist.replaceAll("\\D+", "");
			daything = Integer.parseInt(start_day);
			int total = Integer.parseInt(listData.get(i).get("total").toString());
			int betday = Integer.parseInt(listData.get(i).get("betday").toString());
			for (int j = 1; j <= total; j++) {
				HashMap<String, Object> maplist = new HashMap<String, Object>();
				StringBuffer str = new StringBuffer();
				str.append(listData.get(i).get("time").toString());
				str.insert(str.length() / 2, "—");
				mDatas.add("" + daything);
				maplist.put("date", daything);
				maplist.put("name", listData.get(i).get("name").toString());
				maplist.put("time", str);
				daything = daything + betday;
				listViewData.add(maplist);
			}
		}
		// 设置课程日期
		mCalendarView.setOptionalDate(mDatas);

		adapter = new SimpleAdapter(getActivity(), listViewData, R.layout.item_calendar_list,
				new String[] { "date", "time", "name" },
				new int[] { R.id.calendar_course_date, R.id.calendar_course_time, R.id.calendar_course_title });
		listView.setAdapter(adapter);
	}
}
