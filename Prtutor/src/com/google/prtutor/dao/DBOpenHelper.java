package com.google.prtutor.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1; // 数据库版本号
	private static final String DBNAME = "tutorDB.db"; // 数据库名

	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION); // 重写基类的构造函数
	}

	@Override
	public void onCreate(SQLiteDatabase db) { // 创建数据库
		db.execSQL(
				"create table tb_stu(_id integer primary key autoincrement,pwd varchar(10),stuname varchar(100),city varchar(10),stage varchar(20),phone varchar(20),image vachar(100),sex varchar(10),birth varchar(20),address vachar(100))");
		db.execSQL(
				"create table tb_teacher(_id integer primary key autoincrement,city varchar(10),phone varchar(20),realName varchar(100),teachName varchar(100),pwd varchar(10),image int,desc varchar(300),stuNum int,orgId int,fans int,quotation varchar(100),estimate decimal,feature varchar(300),teachAge int,sex varchar(10), address vachar(100), longitude int,latitude int)");
		db.execSQL(
				"create table tb_course(_id integer primary key autoincrement,teachId int,type varchar(20),start_day varchar(100),course_time varchar(100),classId int,courseName varchar(100),price decimal,container int,betday int,enrollment int,total int,longitude int,latitude int,desc varchar(200),img int,courseClass varchar(20), estimate decimal, FOREIGN KEY(teachId) REFERENCES tb_teacher(_id))");
		db.execSQL(
				"create table tb_order(_id integer primary key autoincrement,name varchar(100), courseId int,stuId int,odstage varchar(20),createTime varchar(20),estimate decimal,FOREIGN KEY(courseId) REFERENCES tb_course(_id))");
		db.execSQL(
				"create table tb_organization(_id integer primary key autoincrement,orgName varchar(50),createTime varchar(20),stuFans int,desc varchar(200),orgPhone varchar(20))");
		db.execSQL("create table tb_records(_id integer primary key autoincrement,words varchar(200))");
		db.execSQL(
				"create table tb_favoriate(_id integer primary key autoincrement,courseId int,name varchar(100),FOREIGN KEY(courseId) REFERENCES tb_course(_id))");
		db.execSQL(
				"create table tb_comment(_id integer primary key autoincrement,courseId int,createTime varchar(20),stuname varchar(100),comment varchar(100),FOREIGN KEY(stuname) REFERENCES tb_stu(stuname),FOREIGN KEY(courseId) REFERENCES tb_course(_id))");
		db.execSQL(
				"create table IF NOT EXISTS recentcity (id integer primary key autoincrement, name varchar(40), date INTEGER)");
		db.execSQL(
				"create table tb_love(_id integer primary key autoincrement,teachId int,name varchar(100),FOREIGN KEY(teachId) REFERENCES tb_teacher(_id))");
	}

	// 覆写基类的onUpgrade方法，以便数据库版本更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
