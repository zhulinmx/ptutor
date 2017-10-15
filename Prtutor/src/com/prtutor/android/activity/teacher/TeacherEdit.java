package com.prtutor.android.activity.teacher;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.TeacherDAO;
import com.prtutor.android.view.HandyTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class TeacherEdit extends BaseActivity implements OnClickListener {
	
	private ImageButton edit_back;
	private HandyTextView tv_tutor_title;
	private Button btn_tutor_ok;
	private EditText et_tutor;
	int editfalg;
	String name,editcontext;
	private TeacherDAO teacherDAO;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutor_edit_commit);
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		editfalg = bundle.getInt("editfalg");
		name = bundle.getString("teachname").toString();
		if(bundle.getString("editcontext") == null){
			editcontext = "";	
		}
		teacherDAO = new TeacherDAO(this);
		initViews();
		initData();
		initEvents();

	}

	@Override
	protected void initViews() {
		edit_back = (ImageButton) findViewById(R.id.tutor_edit_back);
		tv_tutor_title = (HandyTextView) findViewById(R.id.tv_tutor_title);
		btn_tutor_ok = (Button) findViewById(R.id.btn_tutor_ok); 
		et_tutor = (EditText) findViewById(R.id.et_tutor_commit); 
	}

	@Override
	protected void initEvents() {
		edit_back.setOnClickListener(this);
		btn_tutor_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tutor_edit_back:
			defaultFinish();
			break;
		case R.id.btn_tutor_ok:
			String edit_cotext = et_tutor.getText().toString();
			if(editfalg == 0){
				teacherDAO.updateDesc(edit_cotext, name);
			}else{
				teacherDAO.updateQua(edit_cotext, name);
			}
			startActivity(TeacherActivity.class);
			break;
		}
	}
	public void initData(){
		
		if(editfalg == 1) {
			tv_tutor_title.setText("±à¼­ÓïÂ¼");
		}
		et_tutor.setText(editcontext);
	}
}