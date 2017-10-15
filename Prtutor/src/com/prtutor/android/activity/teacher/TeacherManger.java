package com.prtutor.android.activity.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;

public class TeacherManger extends BaseActivity implements OnClickListener {
	
	private LinearLayout ll_home;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teacher_manager);
	}

	@Override
	protected void initViews() {
		ll_home = (LinearLayout) findViewById(R.id.ll_home);
	}

	@Override
	protected void initEvents() {
		ll_home.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_home:
			showShortToast("ll_home");
			break;
		}
	}
}
