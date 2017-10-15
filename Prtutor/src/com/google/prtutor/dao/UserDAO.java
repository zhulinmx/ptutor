package com.google.prtutor.dao;

import android.content.Context;

import com.google.prtutor.model.Tb_stu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
    
	public UserDAO(Context context) {
		helper = new DBOpenHelper(context);// 初始化

	}
	/**
	 * 学生注册
	 */
	public void addUser(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_stu(pwd,stuname,phone,image) values(?,?,?,?)",
				new Object[] {tb_stu.getpwd(),tb_stu.getStuname(), tb_stu.getphone(), tb_stu.getImage() });
	}
	/**
	 * 按照学生（id）查询数据
	 */
	public Cursor queryUserId(int id) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select image, stuname, address from tb_stu where _id ="+id,null);
		return cursor;
	}
	/**
	 * 按照学生（stuname）查询数据
	 */
	public Cursor queryUserName(String stuname) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select image, stuname, address, phone, sex, pwd from tb_stu where stuname ='" + stuname + "'",null);
		return cursor;
	}
	/**
	 * 账户密码是否正确
	 */
	public boolean hasStu(String name,String pwd) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select * from tb_stu where stuname = '" + name + "' and pwd ='" + pwd + "'",null);
		// 判断是否有下一个
		return cursor.moveToNext();
	}
    //更新用户密码
	public void updatePwd(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set pwd = ? where stuname = '" + tb_stu.getStuname() + "' ", new Object[] { tb_stu.getpwd() });
	}
    //更新用户头像
	public void updateImg(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set image = ? where stuname = '" + tb_stu.getStuname() + "'", new Object[] { tb_stu.getImage()});
	}
    //更新用户昵称
	public void updateName(String name) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set stuname = ? ", new Object[] { name });
	}
    //更新用户性别
	public void updateSex(Tb_stu tb_stu) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set sex = ?", new Object[] { tb_stu.getSex() });
	}
    //更新用户地址
	public void updateAddress(String address) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_stu set address = ?", new Object[] { address });
	}
}
