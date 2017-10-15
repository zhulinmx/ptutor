package com.prtutor.android.activity.teacher;

import android.content.DialogInterface;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.BaseDialog;
import com.google.prtutor.R;
import com.google.prtutor.dao.CourseDAO;
import com.prtutor.android.activity.course.AddCourse;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.activity.order.MyOrder;

public class SettingsFragment extends Fragment {
	
	private GridView gridView;
	private CourseDAO courseDAO;
	private BaseDialog mBackDialog;
	private ImageView ivadd;
	// 存储数据的数组列表
	ArrayList<HashMap<String, Object>> listData;
	BaseAdapter adapter;
	private int pos,courseid ;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.teacher_settings, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		courseDAO = new CourseDAO(getActivity());
		SharedPreferences sp = getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);
        int teachId = sp.getInt("teachId", 0);
		initView();
		initEvents();
		initcourse(teachId);
	}
	private void initView() {
		gridView = (GridView) getActivity().findViewById(R.id.tutor_gv_course);
		ivadd = (ImageView) getActivity().findViewById(R.id.iv_tutor_add_course);
	}
	public void initEvents() {
	    // 添加点击事件
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				pos = position;
				courseid = Integer.parseInt(listData.get(pos).get("id").toString());
				mBackDialog = BaseDialog.getDialog(getActivity(), "提示", "确认要删除课程吗？", "确认",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(!courseDAO.hasOrder(courseid)){
									courseDAO.deleteCourse(courseid);
									listData.remove(pos);
									adapter.notifyDataSetChanged();
								}
								dialog.dismiss();

							}
						}, "否", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
				mBackDialog.show();
				return true;
			}
		});
        ivadd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(getActivity().getApplicationContext(), AddCourse.class);
				startActivity(intent);

			}
		});
	}
	/**
	 * 老师课程初始化
	 */
	private void initcourse(int tempid){
		Cursor cursor = courseDAO.queryTutorCourse(tempid);
		int columnsSize = cursor.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("id", cursor.getInt(0));
				map.put("title", cursor.getString(2));
				map.put("img", cursor.getInt(6));
				map.put("class", cursor.getString(7)+"·"+cursor.getString(1));
				map.put("enrollment", "                "+cursor.getInt(8)+"人已报名");
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(getActivity(), listData, R.layout.course_gridview,
				new String[] { "img", "title", "class","enrollment" }, new int[] { R.id.course_gv_img,
						R.id.course_tv_name, R.id.course_tv_type, R.id.course_tv_buy});
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
