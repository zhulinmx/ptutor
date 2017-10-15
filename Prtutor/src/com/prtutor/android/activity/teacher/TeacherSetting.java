package com.prtutor.android.activity.teacher;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.LoginActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.TeacherDAO;
import com.google.prtutor.model.Tb_teacher;
import com.prtutor.android.dialog.EditTextDialog;
import com.prtutor.android.dialog.PwdDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeacherSetting extends BaseActivity implements OnClickListener {

	private LinearLayout lytutorname;
	private LinearLayout lytutorsex;
	private LinearLayout lytutorteachAge;
	private LinearLayout lytutorpwd;
	private LinearLayout lytutorloc;
	private TextView tvname, tvsex, tvage, tvloc;

	private EditTextDialog editDialog;
	private PwdDialog pwdDialog;
	private Context mContext;

	private Intent intent;
	private TeacherDAO teacherDAO;
	private Tb_teacher tb_teacher;
	private String[] sexArry = new String[] { "Ů", "��" };// �Ա�ѡ��
	private String[] locArry = new String[] { "��ˮ������ѧ1��", "��ˮ������ѧ2��" };// ��ַѡ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutor_setting);
		mContext = (Context) TeacherSetting.this;
		teacherDAO = new TeacherDAO(this);
		tb_teacher = new Tb_teacher();
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		String name = bundle.getString("teachname").toString();
		initData(name);
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		lytutorname = (LinearLayout) findViewById(R.id.ly_tutor_name);
		lytutorsex = (LinearLayout) findViewById(R.id.ly_tutor_sex);
		lytutorteachAge = (LinearLayout) findViewById(R.id.ly_tutor_teachAge);
		lytutorpwd = (LinearLayout) findViewById(R.id.ly_tutor_pwd);
		lytutorloc = (LinearLayout) findViewById(R.id.ly_tutor_location);
		tvname = (TextView) findViewById(R.id.tutor_info_tv_name);
		tvsex = (TextView) findViewById(R.id.tutor_info_tv_sex);
		tvage = (TextView) findViewById(R.id.tutor_info_tv_teachAge);
		tvloc = (TextView) findViewById(R.id.tutor_info_tv_loc);
		editDialog = new EditTextDialog(mContext);
		pwdDialog = new PwdDialog(mContext);
		tvname.setText(tb_teacher.getTeachName());
		tvsex.setText(tb_teacher.getSex());
		tvage.setText(tb_teacher.getTeachAge() + "��");
		tvloc.setText(tb_teacher.getAddress());
	}

	@Override
	protected void initEvents() {
		lytutorname.setOnClickListener(this);
		lytutorsex.setOnClickListener(this);
		lytutorteachAge.setOnClickListener(this);
		lytutorpwd.setOnClickListener(this);
		lytutorloc.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tutor_setting_back:
			defaultFinish();
			break;
		case R.id.ly_tutor_name:
			editDialog.setText(tb_teacher.getRealName());
			editDialog.setTitle("�޸�����");
			editDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					editDialog.cancel();
				}
			}, "ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					tb_teacher.setRealName(editDialog.getText().toString().trim());
					teacherDAO.updateRealName(tb_teacher);
					tvname.setText(tb_teacher.getRealName());
					editDialog.cancel();
				}
			});
			editDialog.show();
			break;
		case R.id.ly_tutor_sex:
			showChooseSexDialog();
			break;
		case R.id.ly_tutor_location:
			showChooseLocDialog();
			break;
		case R.id.ly_tutor_teachAge:
			editDialog.setText(tb_teacher.getTeachAge() + "");
			editDialog.setTitle("�޸Ľ���");
			editDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					editDialog.cancel();
				}
			}, "ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					tb_teacher.setTeachAge(Integer.parseInt(editDialog.getText().toString().trim()));
					showShortToast(tb_teacher.getTeachAge() + "");
					teacherDAO.updateteachAge(tb_teacher);
					tvage.setText(tb_teacher.getTeachAge() + "��");
					editDialog.cancel();
				}
			});
			editDialog.show();
			break;
		case R.id.ly_tutor_pwd:
			showChoosePwdDialog();
			break;
		}
	}

	/* �Ա�ѡ�� */
	private void showChooseSexDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);// �Զ���Ի���
		builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2Ĭ�ϵ�ѡ��

			@Override
			public void onClick(DialogInterface dialog, int which) {// which�Ǳ�ѡ�е�λ��
				tb_teacher.setSex(sexArry[which]);
				showShortToast(tb_teacher.getSex());
				teacherDAO.updateSex(tb_teacher);
				tvsex.setText(tb_teacher.getSex());
				dialog.dismiss();
			}
		});
		builder.show();// �õ�������ʾ
	}

	/* ��ַѡ�� */
	private void showChooseLocDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);// �Զ���Ի���
		builder.setSingleChoiceItems(locArry, 0, new DialogInterface.OnClickListener() {// 2Ĭ�ϵ�ѡ��

			@Override
			public void onClick(DialogInterface dialog, int which) {// which�Ǳ�ѡ�е�λ��
				if (which == 0) {
					tb_teacher.setAddress(locArry[which]);
					tb_teacher.setLatitude(30902594);
					tb_teacher.setLongitude(121933025);
					teacherDAO.updateLoc(tb_teacher);
					tvloc.setText(tb_teacher.getAddress());
				}else{
					tb_teacher.setAddress(locArry[which]);
					tb_teacher.setLatitude(30902694);
					tb_teacher.setLongitude(121935025);
					teacherDAO.updateLoc(tb_teacher);
					tvloc.setText(tb_teacher.getAddress());
				}
				dialog.dismiss();
			}
		});
		builder.show();// �õ�������ʾ
	}

	/* �޸����� */
	private void showChoosePwdDialog() {

		pwdDialog.setTitle("�޸�����");
		pwdDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pwdDialog.cancel();
			}
		}, "ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (pwdDialog.getOldText().equals(tb_teacher.getPwd())) {
					tb_teacher.setPwd(pwdDialog.getNewText());
					teacherDAO.updatePwd(tb_teacher);
					showShortToast("�����޸ĳɹ�");
					LoginAgain();
				} else {
					showShortToast("�������������");
				}
				pwdDialog.cancel();
			}
		});
		pwdDialog.show();
	}

	public void LoginAgain() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		intent.putExtra("rolefalg", 1);
		startActivity(intent);
	}

	public void initData(String name) {
		Cursor cursorData = teacherDAO.queryTeacherName(name);
		tb_teacher.setTeachName(name);
		if (cursorData.moveToFirst()) {
			tb_teacher.setRealName(cursorData.getString(8));
			tb_teacher.setSex(cursorData.getString(6));
			tb_teacher.setTeachAge(cursorData.getInt(5));
			tb_teacher.setPwd(cursorData.getString(7));
			tb_teacher.setAddress(cursorData.getString(9));
		}
	}
}
