package com.prtutor.android.activity.teacher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import com.special.ResideMenu.ResideMenu;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.R;
import com.google.prtutor.dao.CommentDAO;
import com.google.prtutor.dao.TeacherDAO;
import com.prtutor.android.activity.course.AddCourse;
import com.prtutor.android.view.HandyTextView;

public class HomeFragment extends Fragment {

	private View parentView;
	private ResideMenu resideMenu;
	private HandyTextView tv_teacher_name;
	private ListView listView;
	private CommentDAO commentDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.teacher_home, container, false);
		return parentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpViews();
		initView();
		initTutor();
	}

	private void setUpViews() {
		TeacherActivity parentActivity = (TeacherActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
		// add gesture operation's ignored views
		FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
		resideMenu.addIgnoredView(ignored_view);

	}

	private void initView() {
		parentView.findViewById(R.id.add_course).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(getActivity().getApplicationContext(), AddCourse.class);
				startActivity(intent);

			}
		});
		listView = (ListView) parentView.findViewById(R.id.comment_tutor_lv);
		commentDAO = new CommentDAO(getActivity());
	}

	public void initTutor() {
		ArrayList<HashMap<String, Object>> listData;
		BaseAdapter adapter;
		SharedPreferences sp = getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE);
		int teachId = sp.getInt("teachId", 0);
		Cursor cursor = commentDAO.queryTutorComment(teachId);
		int columnsSize = cursor.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("stuname", cursor.getString(0));
				map.put("time", cursor.getString(1));
				map.put("comment", cursor.getString(2));
				map.put("courseName", cursor.getString(3));
				map.put("date", cursor.getString(4));
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(getActivity(), listData, R.layout.item_tutor_comment,
				new String[] { "stuname","time", "comment", "courseName", "date" },
				new int[] { R.id.stu_to_name,R.id.tv_tutor_comment_time, R.id.right_comment, R.id.tutor_to_course, R.id.day });
		listView.setAdapter(adapter);
	}
}
