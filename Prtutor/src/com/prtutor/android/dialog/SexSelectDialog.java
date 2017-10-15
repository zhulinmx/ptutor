package com.prtutor.android.dialog;

import com.google.prtutor.BaseDialog;
import com.google.prtutor.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SexSelectDialog extends Dialog {

	private RadioGroup sexgroup;
	private static SexSelectDialog mSexDialog;// 当前的对话框
	private RadioButton RadioButton1;
	private RadioButton RadioButton2;
	private OnCheckedChangeListener CheckedChangeListener1;
	private OnCheckedChangeListener CheckedChangeListener2;
	
	public SexSelectDialog(Context context) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		setContentView(R.layout.include_dialog_sex_select);
		initViews();
		sexSelect();
	}

	private void initViews() {
		sexgroup = (RadioGroup) findViewById(R.id.dialog_sex_sel);
	}

	private void sexSelect() {
		sexgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton sex = (RadioButton) findViewById(checkedId);
				dismiss();
				sex.getText().toString();
			}
		});
	}

}
