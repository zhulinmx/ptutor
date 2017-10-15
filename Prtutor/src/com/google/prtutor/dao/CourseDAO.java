package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_course;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public CourseDAO(Context context) {
		helper = new DBOpenHelper(context);
	}

	public void addCourse(Tb_course tb_course) {
		db = helper.getWritableDatabase();
		db.execSQL(
				"insert into tb_course(courseName,type,course_time,start_day,price,container,betday,total,desc,img,courseClass,teachId) values(?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { tb_course.getCourseName(), tb_course.getType(), tb_course.getCourse_time(),
						tb_course.getStart_day(), tb_course.getPrice(), tb_course.getContainer(), tb_course.getBetday(),
						tb_course.getTotal(), tb_course.getDesc(), tb_course.getCourseImg(), tb_course.getCourseClass(), tb_course.getTeachid() });
		db.close();
	}

	/**
	 * 检查数据库中是否已经有该条记录
	 */
	public boolean hasCourse(String tempCourse) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, courseName from tb_course where courseName like '%" + tempCourse + "%'",
				new String[] { tempCourse });
		// 判断是否有下一个
		return cursor.moveToNext();
	}

	///// **************花式查询**************/////
	/**
	 * 按照课程名（_id）查询数据
	 */
	public Cursor queryCourseId(int id) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_course._id, tb_course.type, tb_course.courseName, tb_course.start_day, tb_course.course_time, tb_course.price, tb_course.container, tb_course.desc, tb_course.img, tb_teacher.longitude, tb_teacher.latitude, tb_course.teachId, tb_teacher.realName, tb_teacher.image, tb_teacher.phone, tb_teacher.desc,tb_course.estimate,tb_course.enrollment,tb_teacher.address,tb_course.betday,tb_course.total from tb_course,tb_teacher where tb_course.teachId=tb_teacher._id and tb_course._id ="
						+ id,
				null);
		return cursor;
	}

	/**
	 * 按照课程名（courseName）查询数据
	 */
	public Cursor queryCourse(String tempCourse) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, type, courseName, start_day, price, container,img from tb_course where courseName like '%"
						+ tempCourse + "%' order by _id desc ",
				null);
		return cursor;
	}

	/**
	 * 按照教师ID（teachId）查询数据
	 */
	public Cursor queryTutorCourse(int tutorId) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id, type, courseName, start_day, price, container, img, courseClass,enrollment from tb_course where teachId ="
						+ tutorId,
				null);
		return cursor;
	}

	/**
	 * 模糊查询课程
	 */
	public Cursor query(String tempCourse) {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select courseName from tb_course where courseName like '%" + tempCourse + "%' ", null);
		return cursor;
	}

	/**
	 * 按照课程类型（type）、评分(estimate)查询数据
	 */
	public Cursor queryCourseRule(String tempCourse, String rule1, String rule2, String rule3) {
		String sql = "select _id as _id, type, courseName, start_day, price, container,img from tb_course where type like '%"
				+ rule3 + "%' and courseName like '%" + tempCourse + "%' and courseClass like '%" + rule1 + "%'";
		if (rule2 == "评分最高") {
			sql = sql + " order by estimate desc";
		}
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
		return cursor;
	}
	/**
	 * 查询课程排行top5
	 */
	public Cursor queryTop() {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select tb_course.courseName,tb_course.img,tb_course.enrollment,tb_teacher.realName,tb_course._id from tb_course,tb_teacher where tb_course.teachId = tb_teacher._id order by tb_course.enrollment desc limit 0,5", null);
		return cursor;
	}
	/**
	 * 课程是否被订购
	 */
	public boolean hasOrder(int tempid) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_order where courseId =" + tempid, null);
		// 判断是否有下一个
		return cursor.moveToNext();
	}

	/**
	 * 删除课程
	 */
	public void deleteCourse(int tempid) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_course where _id =" + tempid);
		db.close();
	}
}
