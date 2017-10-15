package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_teacher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TeacherDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public TeacherDAO(Context context) {
		helper = new DBOpenHelper(context);// ��ʼ��
	}

	/**
	 * ��ʦע��
	 */
	public void addTeacher(Tb_teacher tb_teacher) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_teacher(teachName,phone,pwd,image) values(?,?,?,?)",
				new Object[] { tb_teacher.getTeachName(), tb_teacher.getPhone(), tb_teacher.getPwd(), tb_teacher.getImage()});
	}

	/**
	 * ������ʦid��_id����ѯ����
	 */
	public Cursor queryTeacher(int tempid) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, teachName, image, desc, stuNum, fans, quotation, teachAge, realName from tb_teacher where _id like '%"
						+ tempid + "%' order by _id desc ",
				null);
		return cursor;
	}

	/**
	 * ������ʦteachName��ѯ����
	 */
	public Cursor queryTeacherName(String teachName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select image, desc, stuNum, fans, quotation, teachAge, sex, pwd, realName, address from tb_teacher where teachName = '"
						+ teachName + "'",
				null);
		return cursor;
	}

	/**
	 * ������ʦteachName��ѯid
	 */
	public Cursor queryTeacherId(String teachName) {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select _id from tb_teacher where teachName = '" + teachName + "'", null);
		return cursor;
	}

	/**
	 * �˻������Ƿ���ȷ
	 */
	public boolean hasTeacher(String name, String pwd) {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select * from tb_teacher where teachName = '" + name + "' and pwd ='" + pwd + "'", null);
		// �ж��Ƿ�����һ��
		return cursor.moveToNext();
	}

	// ������ʦquotation
	public void updateQua(String quotation, String name) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_teacher set quotation = ? where teachName = '" + name + "' ", new Object[] { quotation });
	}

	// ������ʦdesc
	public void updateDesc(String desc, String name) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_teacher set desc = ?  where teachName = '" + name + "' ", new Object[] { desc });
	}

	// ������ʦsex
	public void updateSex(Tb_teacher tb_teacher) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_teacher set sex = ?  where teachName = '" + tb_teacher.getTeachName() + "' ",
				new Object[] { tb_teacher.getSex() });
	}

	// ������ʦteachAge
	public void updateteachAge(Tb_teacher tb_teacher) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_teacher set teachAge = ?  where teachName = '" + tb_teacher.getTeachName() + "' ",
				new Object[] { tb_teacher.getTeachAge() });
	}

	// ������ʦrealName
	public void updateRealName(Tb_teacher tb_teacher) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_teacher set realName = ?  where teachName = '" + tb_teacher.getTeachName() + "' ",
				new Object[] { tb_teacher.getRealName() });
	}

	// ������ʦpwd
	public void updatePwd(Tb_teacher tb_teacher) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_teacher set pwd = ?  where teachName = '" + tb_teacher.getTeachName() + "' ",
				new Object[] { tb_teacher.getPwd() });
	}

	// ������ʦaddress
	public void updateLoc(Tb_teacher tb_teacher) {
		db = helper.getWritableDatabase();
		db.execSQL(
				"update tb_teacher set address = ?,longitude = ?,latitude = ? where teachName = '"
						+ tb_teacher.getTeachName() + "' ",
				new Object[] { tb_teacher.getAddress(), tb_teacher.getLongitude(), tb_teacher.getLatitude() });
	}
}
