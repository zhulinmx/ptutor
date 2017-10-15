package com.google.prtutor;

import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.activity.order.AddOrder;
import com.prtutor.android.activity.teacher.TeacherActivity;
import com.prtutor.android.view.WaveView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class RoleSelect extends BaseActivity implements OnClickListener {

	private LinearLayout stu_select;
	private LinearLayout teach_select;
	private WaveView waveView;

	private Intent intent;
	private int rolefalg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_select);
		intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		initViews();
		initEvents();
		waveView.setRunning();
	}

	@Override
	protected void initViews() {
		stu_select = (LinearLayout) findViewById(R.id.stu_select);
		teach_select = (LinearLayout) findViewById(R.id.teach_select);
		waveView = (WaveView) findViewById(R.id.wave);
	}

	@Override
	protected void initEvents() {
		stu_select.setOnClickListener(this);
		teach_select.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.stu_select:
			rolefalg = 0;
			intent.putExtra("rolefalg", rolefalg);
			startActivity(intent);
			break;
		case R.id.teach_select:
			rolefalg = 1;
			intent.putExtra("rolefalg", rolefalg);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
