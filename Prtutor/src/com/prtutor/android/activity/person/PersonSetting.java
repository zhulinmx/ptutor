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
	private String[] sexArry = new String[] { "Ů", "��", "����" };// �Ա�ѡ��

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
				showShortToast("ͼƬ�ϴ��ɹ�");
				showShortToast(Const.DOWNLOAD_URL + result);
				tb_stu.setImage(Const.DOWNLOAD_URL + result);
				userDAO.updateImg(tb_stu);
				fb.display(infoicon, tb_stu.getImage());
				break;
			case 2:
				showShortToast("ͼƬ�ϴ�ʧ��");
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
		dialog.setTitle("���ϡ�");
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
			nickDialog.setText(tb_stu.getStuname().toString());// ��ʼ��dialog
			nickDialog.setTitle("�޸��ǳ�");
			nickDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					nickDialog.cancel();
				}
			}, "ȷ��", new DialogInterface.OnClickListener() {

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
				nickDialog.setText("��");
			} else {
				nickDialog.setText(tb_stu.getAddress().toString());
			} // ��ʼ��dialog
			nickDialog.setTitle("�޸ĵ�ַ");
			nickDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					nickDialog.cancel();
				}
			}, "ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					userDAO.updateAddress(nickDialog.getText().toString().trim());
					nickDialog.cancel();
				}
			});
			nickDialog.show();
			break;
		case R.id.setting_info_pwd:
			pwdDialog.setTitle("�޸�����");
			pwdDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					pwdDialog.cancel();
				}
			}, "ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pwdDialog.getOldText().equals(tb_stu.getpwd())) {
						tb_stu.setPwd(pwdDialog.getNewText());
						userDAO.updatePwd(tb_stu);
						showShortToast("�����޸ĳɹ�,�����µ�¼��");
						LoginAgain();
					} else {
						showShortToast("�������������");
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
		builder.setTitle("����ͷ��");
		String[] items = { "ѡ�񱾵���Ƭ", "����" };
		builder.setNegativeButton("ȡ��", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // ѡ�񱾵���Ƭ
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;
				case TAKE_PICTURE: // ����
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
					// ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					break;
				}
			}
		});
		builder.create().show();
	}

	/* �Ա�ѡ�� */
	private void showChooseSexDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);// �Զ���Ի���
		builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2Ĭ�ϵ�ѡ��

			@Override
			public void onClick(DialogInterface dialog, int which) {// which�Ǳ�ѡ�е�λ��
				showShortToast(sexArry[which]);
				tb_stu.setSex(sexArry[which]);
				userDAO.updateSex(tb_stu);
				tvsex.setText(tb_stu.getSex());
				dialog.dismiss();
			}
		});
		builder.show();// �õ�������ʾ
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // ����������ǿ����õ�
			switch (requestCode) {
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // ��ʼ��ͼƬ���вü�����
				break;
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // ��ʼ��ͼƬ���вü�����
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // �øղ�ѡ��ü��õ���ͼƬ��ʾ�ڽ�����
					userDAO.updateImg(tb_stu);
				}
				break;
			}
		}
	}

	/**
	 * �ü�ͼƬ����ʵ��
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
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param
	 * 
	 * @param data
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = ImageUtils.toRoundBitmap(photo, tempUri); // ���ʱ���ͼƬ�Ѿ��������Բ�ε���
			uploadPic(photo);
		}
	}

	private void uploadPic(Bitmap bitmap) {
		// �ϴ���������
		// ... �����������Bitmapת����file��Ȼ��õ�file��url�����ļ��ϴ�����
		// ע������õ���ͼƬ�Ѿ���Բ��ͼƬ��
		// bitmap��û������Բ�δ���ģ����Ѿ����ü���
		final String imagePath = ImageUtils.savePhoto(bitmap,
				Environment.getExternalStorageDirectory().getAbsolutePath(),
				String.valueOf(System.currentTimeMillis()));
		Log.e("imagePath", imagePath + "");
		showShortToast(imagePath);
		// TODO
		// ��������� http request.����������ز���
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
	 * ѧԱ��Ϣ��ʼ��
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
