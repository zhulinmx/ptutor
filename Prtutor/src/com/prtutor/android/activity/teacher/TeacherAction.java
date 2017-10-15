package com.prtutor.android.activity.teacher;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.Next_Search;
import com.google.prtutor.R;
import com.google.prtutor.dao.CourseDAO;
import com.google.prtutor.dao.FavoriteDAO;
import com.google.prtutor.dao.TeacherDAO;
import com.google.prtutor.model.Tb_favoriate;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.view.HandyTextView;
import com.prtutor.android.view.RoundImageview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class TeacherAction extends BaseActivity implements OnClickListener {

	private GridView gridView;
	private HandyTextView thname;
	private HandyTextView th_name;
	private HandyTextView tr_quotation;
	private HandyTextView tr_fans;
	private HandyTextView tr_teachAge;
	private TeacherDAO teacherDAO;
	private CourseDAO courseDAO;
	private ImageButton btn_guanzhu, btn_back;
	private RoundImageview rv_tutor_icon;
	private Intent intent;
	private int tutorId;
	// 存储数据的数组列表
	ArrayList<HashMap<String, Object>> listData;
	private static int fari_flag;
	private FavoriteDAO ftDAO;
	private Tb_favoriate tb_favoriate;
	int fans;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_detail);
		teacherDAO = new TeacherDAO(this);
		courseDAO = new CourseDAO(this);
		ftDAO = new FavoriteDAO(this);
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		tutorId = bundle.getInt("trId");
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String username = sp.getString("username", "");
		tb_favoriate = new Tb_favoriate(tutorId, username);
		fans = ftDAO.CountLove(tutorId);
		initViews();
		initEvents();
		if (ftDAO.hasLove(tb_favoriate)) {
			selectImg(0);
		} else {
			selectImg(1);
		}
		queryData(tutorId);
		initcourse(tutorId);
	}

	@Override
	protected void initViews() {
		thname = (HandyTextView) findViewById(R.id.teacher_name);
		th_name = (HandyTextView) findViewById(R.id.tr_name);
		tr_quotation = (HandyTextView) findViewById(R.id.tr_quotation);
		tr_fans = (HandyTextView) findViewById(R.id.tr_fans);
		tr_teachAge = (HandyTextView) findViewById(R.id.tr_teachAge);
		btn_guanzhu = (ImageButton) findViewById(R.id.teacher_guanzhu);
		btn_back = (ImageButton) findViewById(R.id.teacher_back);
		gridView = (GridView) findViewById(R.id.tutor_gridView);
		rv_tutor_icon = (RoundImageview) findViewById(R.id.rv_tutor_img);
	}

	@Override
	protected void initEvents() {
		btn_guanzhu.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		// 添加点击事件
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				int courseid = Integer.parseInt(listData.get(pos).get("id").toString());
				Intent intent = new Intent(TeacherAction.this, CourseAction.class);
				intent.putExtra("courseId", courseid);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.teacher_guanzhu:
			btn_guanzhu.setImageResource(R.drawable.tutor_love_pressed);
			if (fari_flag == 0) {
				setSelect(0);
			} else {
				setSelect(1);
			}
			break;
		case R.id.teacher_back:
			finish();
			break;
		}

	}
	private void setSelect(int i) {
		switch (i) {
		case 0:
			selectImg(0);
			ftDAO.addLove(tb_favoriate);
			break;
		case 1:
			selectImg(1);
			ftDAO.deleteLove(tb_favoriate);
			break;
		}
	}
	private void selectImg(int i) {
		switch (i) {
		case 0:
			btn_guanzhu.setImageResource(R.drawable.tutor_love_pressed);
			fari_flag = 1;
			break;
		case 1:
			btn_guanzhu.setImageResource(R.drawable.tutor_love);
			fari_flag = 0;
			break;
		}
	}
	/**
	 * 老师信息初始化
	 */
	private void queryData(int tempid) {
		Cursor cursor = teacherDAO.queryTeacher(tempid);
		if (cursor.moveToFirst()) {
			int img = cursor.getInt(2);
			String teachName = cursor.getString(cursor.getColumnIndex("realName"));
			String quotation = cursor.getString(cursor.getColumnIndex("quotation"));
			int teachAge = cursor.getInt(7);
			if(teachName == null){
				teachName = "";
			}
			if(quotation == null){
				quotation = "无";
			}
			thname.setText(teachName);
			th_name.setText(teachName);
			tr_quotation.setText("个性语录："+quotation);
			tr_teachAge.setText("教龄："+teachAge+"年");
			rv_tutor_icon.setImageResource(img);
		}
		tr_fans.setText("粉丝："+fans);
	}

	/**
	 * 老师课程初始化
	 */
	private void initcourse(int tempid) {
		BaseAdapter adapter;
		Cursor cursor = courseDAO.queryTutorCourse(tempid);
		int columnsSize = cursor.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("id", cursor.getInt(0));
				map.put("title", cursor.getString(2));
				map.put("img", cursor.getInt(6));
				map.put("class", cursor.getString(7) + "·" + cursor.getString(1));
				map.put("enrollment", "                " + cursor.getInt(8) + "人已报名");
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.course_gridview,
				new String[] { "img", "title", "class", "enrollment" },
				new int[] { R.id.course_gv_img, R.id.course_tv_name, R.id.course_tv_type, R.id.course_tv_buy });
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}