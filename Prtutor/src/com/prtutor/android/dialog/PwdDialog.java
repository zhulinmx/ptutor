package com.prtutor.android.dialog;

import com.google.prtutor.BaseDialog;
import com.google.prtutor.R;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class PwdDialog extends BaseDialog {

	private EditText oldEt;
	private EditText newEt;

	public PwdDialog(Context context) {
		super(context);
		
		setDialogContentView(R.layout.include_dialog_edittext_new);
		oldEt = (EditText) findViewById(R.id.dialog_edittext_old);
		newEt = (EditText) findViewById(R.id.dialog_edittext_new);
	}

	@Override
	public void setTitle(CharSequence text) {
		super.setTitle(text);

	}

	public void setButton(CharSequence text, DialogInterface.OnClickListener listener) {
		super.setButton1(text, listener);
	}

	public void setButton(CharSequence text1, DialogInterface.OnClickListener listener1, CharSequence text2,
			DialogInterface.OnClickListener listener2) {
		super.setButton1(text1, listener1);
		super.setButton2(text2, listener2);
	}

	public String getOldText() {
		if (isNull(oldEt)) {
			return null;
		}
		return oldEt.getText().toString().trim();
	}
	public String getNewText() {
	if (isNull(oldEt)) {
			return null;
		}
		return newEt.getText().toString().trim();
	}
	private boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		if (text != null && text.length() > 0) {
			return false;
		}
		return true;
	}
}
