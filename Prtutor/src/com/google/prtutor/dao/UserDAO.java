package com.google.prtutor.dao;

import android.content.Context;

import com.google.prtutor.model.Tb_stu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
    
	public UserDAO(Context context) {
		helper = new DBOpenHelper(context);// ��ʼ��

	}
	/**
	 * ѧ��ע��
	 */
	public void addUser(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_stu(pwd,stuname,phone,image) values(?,?,?,?)",
				new Object[] {tb_stu.getpwd(),tb_stu.getStuname(), tb_stu.getphone(), tb_stu.getImage() });
	}
	/**
	 * ����ѧ����id����ѯ����
	 */
	public Cursor queryUserId(int id) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select image, stuname, address from tb_stu where _id ="+id,null);
		return cursor;
	}
	/**
	 * ����ѧ����stuname����ѯ����
	 */
	public Cursor queryUserName(String stuname) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select image, stuname, address, phone, sex, pwd from tb_stu where stuname ='" + stuname + "'",null);
		return cursor;
	}
	/**
	 * �˻������Ƿ���ȷ
	 */
	public boolean hasStu(String name,String pwd) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select * from tb_stu where stuname = '" + name + "' and pwd ='" + pwd + "'",null);
		// �ж��Ƿ�����һ��
		return cursor.moveToNext();
	}
    //�����û�����
	public void updatePwd(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set pwd = ? where stuname = '" + tb_stu.getStuname() + "' ", new Object[] { tb_stu.getpwd() });
	}
    //�����û�ͷ��
	public void updateImg(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set image = ? where stuname = '" + tb_stu.getStuname() + "'", new Object[] { tb_stu.getImage()});
	}
    //�����û��ǳ�
	public void updateName(String name) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set stuname = ? ", new Object[] { name });
	}
    //�����û��Ա�
	public void updateSex(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set sex = ?", new Object[] { tb_stu.getSex() });
	}
    //�����û���ַ
	public void updateAddress(String address) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set address = ?", new Object[] { address });
	}
}
