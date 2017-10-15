package com.google.prtutor;

import com.google.prtutor.dao.*;
import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.activity.teacher.TeacherActivity;
import com.prtutor.android.view.HandyTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText txtlogin;
	private EditText pwdlogin;
	private Button btnlogin;
	private HandyTextView login_title;
	private TextView tv_register;
	private ImageButton login_back;

	private String username;
	private String password;
	private int falg = 0;
	private int rolefalg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		rolefalg = bundle.getInt("rolefalg");
		initViews();
		initEvents();

	}

	@Override
	protected void initViews() {
		btnlogin = (Button) findViewById(R.id.login_btn_login);
		txtlogin = (EditText) findViewById(R.id.login_et_account);
		pwdlogin = (EditText) findViewById(R.id.login_et_pwd);
		login_title = (HandyTextView) findViewById(R.id.login_title);
		tv_register = (TextView) findViewById(R.id.Register_accout);
		login_back = (ImageButton) findViewById(R.id.login_back);
		if (rolefalg == 1) {
			login_title.setText("老师登录");
		}
	}

	@Override
	protected void initEvents() {
		btnlogin.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		login_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_btn_login:
			doLogin();
			break;
		case R.id.login_back:
			finish();
			break;
		case R.id.Register_accout:
			Intent intent = new Intent();
			intent.setClass(this, RegisterActivity.class);
			intent.putExtra("rolefalg", rolefalg);
			startActivity(intent);
			break;
		}
	}

	private void doLogin() {

		username = txtlogin.getText().toString().trim();
		password = pwdlogin.getText().toString().trim();
		if (username.equals("")) {
			showAlertDialog("O(∩_∩)O", "用户名不能为空!");
			falg = 1;
		}
		if (password.equals("")) {
			showAlertDialog("O(∩_∩)O", "密码不能为空!");
			falg = 1;
		}
		if (falg == 0 && rolefalg == 0) {
			UserDAO userDAO = new UserDAO(this);
			if (userDAO.hasStu(username, password)) {
				showShortToast("登录成功");
				SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
				// 获取Editor对象
				Editor editor = sp.edit();
				// 设置用户名和密码
				editor.putString("username", username);
				editor.putString("password", password);
				// 提交
				editor.commit();
				startActivity(MainTabActivity.class);
			} else {
				showShortToast("用户名或者密码出错");
			}
		}
		if (falg == 0 && rolefalg == 1) {
			TeacherDAO teacherDAO = new TeacherDAO(this);
			if (teacherDAO.hasTeacher(username, password)) {
				showShortToast("登录成功");
				SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
				// 获取Editor对象
				Editor editor = sp.edit();
				// 设置用户名和密码
				Cursor cursor = teacherDAO.queryTeacherId(username);
				int tutorid = 0;
				if (cursor.moveToFirst()) {
					tutorid = cursor.getInt(cursor.getColumnIndex("_id"));
				}
				editor.putString("teachname", username);
				editor.putInt("teachId", tutorid);
				editor.putString("pwd", password);
				// 提交
				editor.commit();
				startActivity(TeacherActivity.class);
			} else {
				showShortToast("用户名或者密码出错");
			}

		}
	}
}
