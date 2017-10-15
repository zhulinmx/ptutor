package com.google.prtutor;

import com.google.prtutor.dao.TeacherDAO;
import com.google.prtutor.dao.UserDAO;
import com.google.prtutor.model.Tb_stu;
import com.google.prtutor.model.Tb_teacher;
import com.prtutor.android.view.HandyTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private EditText et_phone;
	private EditText et_name;
	private EditText et_pwd;
	private Button btn_register;
	private HandyTextView tv_title;

	private Intent intent;
	private String name;
	private String phone;
	private String pwd;
	private int rolefalg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		rolefalg = bundle.getInt("rolefalg");
		initViews();
		initEvents();

	}

	@Override
	protected void initViews() {
		et_phone = (EditText) findViewById(R.id.register_et_phone);
		et_name = (EditText) findViewById(R.id.register_et_name);
		et_pwd = (EditText) findViewById(R.id.register_et_pwd);
		btn_register = (Button) findViewById(R.id.btn_register);
		tv_title = (HandyTextView) findViewById(R.id.register_title);
		if(rolefalg == 1){
			tv_title.setText("老师注册");
		}
	}

	@Override
	protected void initEvents() {
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_register:
			initdata();
			if (rolefalg == 0) {
				Tb_stu tb_stu = new Tb_stu(pwd, name, phone);
				tb_stu.setImage("");
				UserDAO userDAO = new UserDAO(this);
				userDAO.addUser(tb_stu);
			} else {
				Tb_teacher tb_teacher = new Tb_teacher(pwd, name, phone);
				tb_teacher.setImage(R.drawable.tutorimg_four);//老师图片
				TeacherDAO teacherDAO = new TeacherDAO(this);
				teacherDAO.addTeacher(tb_teacher);
			}
			showShortToast("您来啦:"+name);
			defaultFinish();
			break;
		}
	}

	public void initdata() {
		name = et_name.getText().toString().trim();
		phone = et_phone.getText().toString().trim();
		pwd = et_pwd.getText().toString().trim();
	}
}
