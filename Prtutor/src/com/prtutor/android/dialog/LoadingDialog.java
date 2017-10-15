package com.prtutor.android.dialog;


import android.app.Dialog;
import android.content.Context;

public class LoadingDialog extends Dialog {

	private String mText;

	public LoadingDialog(Context context, String text) {
		super(context);
		mText = text;
		init();
	}

	private void init() {
	}

	public void setText(String text) {
		mText = text;
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}
}
