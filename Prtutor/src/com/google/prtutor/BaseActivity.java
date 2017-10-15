package com.google.prtutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.prtutor.android.dialog.LoadingDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


public abstract class BaseActivity extends FragmentActivity {

	public LoadingDialog ldDialog;
	/**
	 * ��Ļ�Ŀ�ȡ��߶ȡ��ܶ�
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;

	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;
	}

	@Override
	protected void onDestroy() {
		clearAsyncTask();
		super.onDestroy();
	}

	/** ��ʼ����ͼ **/
	protected abstract void initViews();

	/** ��ʼ���¼� **/
	protected abstract void initEvents();

	protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
		mAsyncTasks.add(asyncTask.execute());
	}

	protected void clearAsyncTask() {
		Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks
				.iterator();
		while (iterator.hasNext()) {
			AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
			if (asyncTask != null && !asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		mAsyncTasks.clear();
	}

	protected void showLoadingDialog(String text) {
		if (text != null) {
			ldDialog.setText(text);
		}
		ldDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (ldDialog.isShowing()) {
			ldDialog.dismiss();
		}
	}

	/** ������ʾToast��ʾ(����res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** ������ʾToast��ʾ(����String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** ��ʱ����ʾToast��ʾ(����res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** ��ʱ����ʾToast��ʾ(����String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** 
	  ��ʾ�Զ���Toast��ʾ(����res) 
	protected void showCustomToast(int resId) {
		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text))
				.setText(getString(resId));
		Toast toast = new Toast(BaseActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	 ��ʾ�Զ���Toast��ʾ(����String) 
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(BaseActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}**/

	/** Debug���Log��־ **/
	protected void showLogDebug(String tag, String msg) {
		Log.d(tag, msg);
	}

	/** Error���Log��־ **/
	protected void showLogError(String tag, String msg) {
		Log.e(tag, msg);
	}

	/** ͨ��Class��ת���� **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** ����Bundleͨ��Class��ת���� **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** ͨ��Action��ת���� **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** ����Bundleͨ��Action��ת���� **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** ���б�������ݵĶԻ��� **/
	protected AlertDialog showAlertDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).show();
		return alertDialog;
	}

	/** ���б��⡢���ݡ�������ť�ĶԻ��� **/
	protected AlertDialog showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** ���б��⡢���ݡ�ͼ�ꡢ������ť�ĶԻ��� **/
	protected AlertDialog showAlertDialog(String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).setIcon(icon)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** Ĭ���˳� **/
	protected void defaultFinish() {
		super.finish();
	}
}
