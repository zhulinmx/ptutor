package com.prtutor.android.activity.course;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.CourseDAO;
import com.google.prtutor.model.Tb_course;
import com.google.prtutor.timeselector.JustListView;
import com.google.prtutor.timeselector.PopuTextView;
import com.google.prtutor.timeselector.SelectView;
import com.google.prtutor.timeselector.SelectorAdapter;
import com.google.prtutor.timeselector.SelectorContanst;
import com.google.prtutor.timeselector.TimeSelectorView;

public class AddCourse extends BaseActivity implements OnClickListener {
	private TextView tv_data;
	private TextView tv_time;
	private Button btn_ok;
	private Button btn_cancel;
	private EditText et_crname;
	private EditText et_crtime;
	private EditText et_crprice;
	private EditText et_crdesc;
	private EditText et_cr_between;
	private EditText et_crstu;
	private RadioGroup type;
	private String course_type;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_add_course);
		initViews();
		initEvents();

	}

	@Override
	protected void initViews() {
		tv_data = (TextView) findViewById(R.id.mTextViewId);
		tv_time = (TextView) findViewById(R.id.TimeTextViewId);
		btn_ok = (Button) findViewById(R.id.course_finish);
		btn_cancel = (Button) findViewById(R.id.course_dis);
		et_crname = (EditText) findViewById(R.id.et_course_name);
		et_crtime = (EditText) findViewById(R.id.et_cr_time);
		et_crprice = (EditText) findViewById(R.id.et_cr_price);
		et_crdesc = (EditText) findViewById(R.id.et_cr_desc);
		et_crstu = (EditText) findViewById(R.id.et_stunum);
		et_cr_between = (EditText) findViewById(R.id.et_cr_between);
		type = (RadioGroup) findViewById(R.id.rg_type);
		spinner = (Spinner) findViewById(R.id.sp_type);
	}

	@Override
	protected void initEvents() {
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		type.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == 0){
					et_crstu.setVisibility(View.GONE);
					showLongToast("checkedId"+checkedId);
				}
				RadioButton tp = (RadioButton) findViewById(checkedId);
				course_type = tp.getText().toString();
			}
		});
	}

	@Override
	public void onClick(View v) {

		String date = tv_data.getText().toString();
		String time = tv_time.getText().toString();
		String crname = et_crname.getText().toString();
		String crtime = et_crtime.getText().toString();
		String crprice = et_crprice.getText().toString();
		String crdesc = et_crdesc.getText().toString();
		String crstu = et_crstu.getText().toString();
		String crbet = et_cr_between.getText().toString();
		String crspinner = spinner.getSelectedItem().toString();
		SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
	    int tutorId = sp.getInt("teachId",0);
        switch (v.getId()) {

		case R.id.course_finish:

			int coursefalg = 0;
			if (date.equals("")) {
				showShortToast("请重新选择日期！");
				coursefalg = 1;
			}
			if (time.equals("")) {
				showShortToast("请重新选择时间！");
				coursefalg = 1;
			}
			if (crname.equals("")) {
				showShortToast("请填写课程名称！");
				coursefalg = 1;
			}
			if (crstu.equals("")) {
				showShortToast("请填写容纳人数！");
				coursefalg = 1;
			}
			if (crbet.equals("")) {
				showShortToast("请填写间隔天数！");
				coursefalg = 1;
			}
			if (crprice.equals("")) {
				showShortToast("请填写课程价格！");
				coursefalg = 1;
			}
			if (crdesc.equals("")) {
				showShortToast("请填写课程简介！");
				coursefalg = 1;
			}
			if (coursefalg == 0) {
				// 添加课程
				Tb_course tb_course = new Tb_course(crname, course_type, date, time, Float.parseFloat(crprice),
						Integer.parseInt(crstu), Integer.parseInt(crbet), Integer.parseInt(crtime), crdesc, crspinner,tutorId);
				tb_course.setCourseImg(R.drawable.course_imgele);
				CourseDAO courseDAO = new CourseDAO(this);
				courseDAO.addCourse(tb_course);
				defaultFinish();
			}
			break;
		case R.id.course_dis:
			defaultFinish();
			break;
		}
	}

}
