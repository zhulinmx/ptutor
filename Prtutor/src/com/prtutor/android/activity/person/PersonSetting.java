package com.prtutor.android.activity.person;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.tsz.afinal.FinalBitmap;

import com.prtutor.android.util.Const;
import com.prtutor.android.util.ServerUtils;
import com.google.prtutor.BaseActivity;
import com.google.prtutor.LoginActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.CourseDAO;
import com.google.prtutor.dao.UserDAO;
import com.google.prtutor.model.Tb_stu;
import com.prtutor.android.activity.maintabs.MainTabActivity;
import com.prtutor.android.dialog.EditTextDialog;
import com.prtutor.android.dialog.PwdDialog;
import com.prtutor.android.dialog.SexSelectDialog;
import com.prtutor.android.util.HttpUtils;
import com.prtutor.android.util.HttpUtils.CallBack;
import com.prtutor.android.util.ImageUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalBitmap;

public class PersonSetting extends BaseActivity implements OnClickListener {

	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;

	private LinearLayout infoname;
	private LinearLayout infosex;
	private LinearLayout infoimg;
	private LinearLayout infoplace;
	private LinearLayout infopwd;
	private ImageButton personalback;
	private ImageView infoicon;
	private EditTextDialog nickDialog;
	private PwdDialog pwdDialog;
	private TextView tvsex;
	private Context mContext;
	private String[] sexArry = new String[] { "女", "男", "保密" };// 性别选择

	private UserDAO userDAO;
	private Tb_stu tb_stu;
	private Cursor cursorData;
	private int stuid = 1;

	ProgressDialog dialog = null;
	String result = "";
	FinalBitmap fb = null;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dialog.dismiss();
			switch (msg.what) {
			case 1:
				showShortToast("图片上传成功");
				showShortToast(Const.DOWNLOAD_URL + result);
				tb_stu.setImage(Const.DOWNLOAD_URL + result);
				userDAO.updateImg(tb_stu);
				fb.display(infoicon, tb_stu.getImage());
				break;
			case 2:
				showShortToast("图片上传失败");
				break;
			default:
				break;
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_setting);
		mContext = (Context) PersonSetting.this;
		dialog = new ProgressDialog(this);
		fb = FinalBitmap.create(this);
		dialog.setTitle("马上…");
		tb_stu = new Tb_stu();
		userDAO = new UserDAO(this);
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String username = sp.getString("username", "");
		cursorData = userDAO.queryUserName(username);
		queryData();
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		infoname = (LinearLayout) findViewById(R.id.setting_info_name);
		infosex = (LinearLayout) findViewById(R.id.setting_info_sex);
		infoimg = (LinearLayout) findViewById(R.id.setting_info_img);
		infoplace = (LinearLayout) findViewById(R.id.setting_info_address);
		infopwd = (LinearLayout) findViewById(R.id.setting_info_pwd);
		personalback = (ImageButton) findViewById(R.id.personal_back);
		infoicon = (ImageView) findViewById(R.id.info_icon);
		fb.display(infoicon, tb_stu.getImage());
		tvsex = (TextView) findViewById(R.id.setting_info_tv_sex);
		if (tb_stu.getSex() == null) {
			tvsex.setText("");
		} else {
			tvsex.setText(tb_stu.getSex());
		}
		nickDialog = new EditTextDialog(mContext);
		pwdDialog = new PwdDialog(mContext);
	}

	@Override
	protected void initEvents() {
		infoname.setOnClickListener(this);
		infosex.setOnClickListener(this);
		infoimg.setOnClickListener(this);
		infoplace.setOnClickListener(this);
		infopwd.setOnClickListener(this);
		personalback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.setting_info_name:
			nickDialog.setText(tb_stu.getStuname().toString());// 初始化dialog
			nickDialog.setTitle("修改昵称");
			nickDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					nickDialog.cancel();
				}
			}, "确认", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					userDAO.updateName(nickDialog.getText().toString().trim());
					nickDialog.cancel();
				}
			});
			nickDialog.show();
			break;
		case R.id.setting_info_sex:
			showChooseSexDialog();
			break;
		case R.id.setting_info_img:
			showChoosePicDialog();
			break;
		case R.id.setting_info_address:
			if (tb_stu.getAddress() == null) {
				nickDialog.setText("无");
			} else {
				nickDialog.setText(tb_stu.getAddress().toString());
			} // 初始化dialog
			nickDialog.setTitle("修改地址");
			nickDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					nickDialog.cancel();
				}
			}, "确认", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					userDAO.updateAddress(nickDialog.getText().toString().trim());
					nickDialog.cancel();
				}
			});
			nickDialog.show();
			break;
		case R.id.setting_info_pwd:
			pwdDialog.setTitle("修改密码");
			pwdDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					pwdDialog.cancel();
				}
			}, "确认", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pwdDialog.getOldText().equals(tb_stu.getpwd())) {
						tb_stu.setPwd(pwdDialog.getNewText());
						userDAO.updatePwd(tb_stu);
						showShortToast("密码修改成功,请重新登录！");
						LoginAgain();
					} else {
						showShortToast("旧密码输入错误");
					}
					pwdDialog.cancel();
				}
			});
			pwdDialog.show();
			break;
		case R.id.personal_back:
			startActivity(MainTabActivity.class);
			break;
		}

	}

	public void LoginAgain() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		intent.putExtra("rolefalg", 0);
		startActivity(intent);
	}

	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // 选择本地照片
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;
				case TAKE_PICTURE: // 拍照
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					break;
				}
			}
		});
		builder.create().show();
	}

	/* 性别选择 */
	private void showChooseSexDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
		builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

			@Override
			public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
				showShortToast(sexArry[which]);
				tb_stu.setSex(sexArry[which]);
				userDAO.updateSex(tb_stu);
				tvsex.setText(tb_stu.getSex());
				dialog.dismiss();
			}
		});
		builder.show();// 让弹出框显示
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
				break;
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
					userDAO.updateImg(tb_stu);
				}
				break;
			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param
	 * 
	 * @param data
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = ImageUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			uploadPic(photo);
		}
	}

	private void uploadPic(Bitmap bitmap) {
		// 上传至服务器
		// ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
		// 注意这里得到的图片已经是圆形图片了
		// bitmap是没有做个圆形处理的，但已经被裁剪了
		final String imagePath = ImageUtils.savePhoto(bitmap,
				Environment.getExternalStorageDirectory().getAbsolutePath(),
				String.valueOf(System.currentTimeMillis()));
		Log.e("imagePath", imagePath + "");
		showShortToast(imagePath);
		// TODO
		// 在这里进行 http request.网络请求相关操作
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				result = ServerUtils.formUpload(Const.UPLOAD_URL, imagePath);
				Log.e("result", result);
				if (!TextUtils.isEmpty(result)) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(2);
				}
			}
		}).start();
	}

	/**
	 * 学员信息初始化
	 */
	private void queryData() {

		if (cursorData.moveToFirst()) {
			tb_stu.setStuname(cursorData.getString(1));
			tb_stu.setAddress(cursorData.getString(2));
			tb_stu.setSex(cursorData.getString(4));
			tb_stu.setImage(cursorData.getString(0));
			tb_stu.setPwd(cursorData.getString(5));
		}
	}
}
