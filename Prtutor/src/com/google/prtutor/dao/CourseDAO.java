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
	 * ������ݿ����Ƿ��Ѿ��и�����¼
	 */
	public boolean hasCourse(String tempCourse) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, courseName from tb_course where courseName like '%" + tempCourse + "%'",
				new String[] { tempCourse });
		// �ж��Ƿ�����һ��
		return cursor.moveToNext();
	}

	///// **************��ʽ��ѯ**************/////
	/**
	 * ���տγ�����_id����ѯ����
	 */
	public Cursor queryCourseId(int id) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_course._id, tb_course.type, tb_course.courseName, tb_course.start_day, tb_course.course_time, tb_course.price, tb_course.container, tb_course.desc, tb_course.img, tb_teacher.longitude, tb_teacher.latitude, tb_course.teachId, tb_teacher.realName, tb_teacher.image, tb_teacher.phone, tb_teacher.desc,tb_course.estimate,tb_course.enrollment,tb_teacher.address,tb_course.betday,tb_course.total from tb_course,tb_teacher where tb_course.teachId=tb_teacher._id and tb_course._id ="
						+ id,
				null);
		return cursor;
	}

	/**
	 * ���տγ�����courseName����ѯ����
	 */
	public Cursor queryCourse(String tempCourse) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, type, courseName, start_day, price, container,img from tb_course where courseName like '%"
						+ tempCourse + "%' order by _id desc ",
				null);
		return cursor;
	}

	/**
	 * ���ս�ʦID��teachId����ѯ����
	 */
	public Cursor queryTutorCourse(int tutorId) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id, type, courseName, start_day, price, container, img, courseClass,enrollment from tb_course where teachId ="
						+ tutorId,
				null);
		return cursor;
	}

	/**
	 * ģ����ѯ�γ�
	 */
	public Cursor query(String tempCourse) {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select courseName from tb_course where courseName like '%" + tempCourse + "%' ", null);
		return cursor;
	}

	/**
	 * ���տγ����ͣ�type��������(estimate)��ѯ����
	 */
	public Cursor queryCourseRule(String tempCourse, String rule1, String rule2, String rule3) {
		String sql = "select _id as _id, type, courseName, start_day, price, container,img from tb_course where type like '%"
				+ rule3 + "%' and courseName like '%" + tempCourse + "%' and courseClass like '%" + rule1 + "%'";
		if (rule2 == "�������") {
			sql = sql + " order by estimate desc";
		}
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
		return cursor;
	}
	/**
	 * ��ѯ�γ�����top5
	 */
	public Cursor queryTop() {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select tb_course.courseName,tb_course.img,tb_course.enrollment,tb_teacher.realName,tb_course._id from tb_course,tb_teacher where tb_course.teachId = tb_teacher._id order by tb_course.enrollment desc limit 0,5", null);
		return cursor;
	}
	/**
	 * �γ��Ƿ񱻶���
	 */
	public boolean hasOrder(int tempid) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_order where courseId =" + tempid, null);
		// �ж��Ƿ�����һ��
		return cursor.moveToNext();
	}

	/**
	 * ɾ���γ�
	 */
	public void deleteCourse(int tempid) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_course where _id =" + tempid);
		db.close();
	}
}
