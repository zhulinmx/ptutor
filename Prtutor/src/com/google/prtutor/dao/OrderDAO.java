package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_order;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OrderDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public OrderDAO(Context context) {
		helper = new DBOpenHelper(context);
	}

	/**
	 * 订购课程人数
	 */
	public int countNum(int tempid) {
		db = helper.getWritableDatabase();
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_order where courseId =" + tempid, null);
		return cursor.getCount();
	}

	/**
	 * 下单
	 */
	public void addOrder(Tb_order tb_order) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_order(courseId,name,odstage,createTime) values(?,?,?,?)", new Object[] {
				tb_order.getCourseId(), tb_order.getUsername(), tb_order.getOdstage(), tb_order.getCreateTime() });
		// 更新报名人数enrollment
		db.execSQL("update tb_course set enrollment = ? where _id =" + tb_order.getCourseId(),
				new Object[] { countNum(tb_order.getCourseId()) });
		db.close();
	}

	/**
	 * 撤销订单
	 */
	public void deleteOrder(int orserid, int courseid) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_order where _id =" + orserid);
		// 更新报名人数enrollment
		db.execSQL("update tb_course set enrollment = ? where _id =" + courseid, new Object[] { countNum(courseid) });
		db.close();
	}

	/**
	 * 更新订单状态(评价和评分)
	 */
	public void updateOdstage(int orserid, float starRating, int courseid) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_order set odstage = ? where _id =" + orserid, new Object[] { "已评价" });
		db.execSQL("update tb_order set estimate = ?  where _id =" + orserid, new Object[] { starRating });
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select AVG(estimate) from tb_order where courseId =" + courseid, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				float avgEstimate = cursor.getFloat(0);
				db.execSQL("update tb_course set estimate = ? where _id =" + courseid, new Object[] { avgEstimate });
			}
		}
		db.close();
	}

	///// **************花式查询**************/////
	/**
	 * 按照学生姓名查询订单
	 */
	public Cursor queryOrder(String name) {

		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_order._id, tb_order.courseId, tb_order.odstage, tb_order.createTime,tb_course.courseName,tb_course.container,tb_course.total,tb_course.betday,tb_course.start_day,tb_course.img,tb_course.price,tb_order.estimate,tb_course.course_time from tb_order,tb_course where tb_course._id=tb_order.courseId and tb_order.name ='"
						+ name + "'",
				null);
		return cursor;
	}

	/**
	 * 按照老师（TeacherId）和（odstage）查询订单
	 */
	public Cursor queryOrderTr(int trId,String state) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_order._id, tb_order.courseId, tb_order.odstage,tb_order.name,tb_course.img,tb_order.createTime,tb_course.courseName from tb_order,tb_course,tb_teacher where tb_course._id=tb_order.courseId and tb_course.teachId=tb_teacher._id and tb_order.odstage like '%" + state + "%' and tb_course.teachId ="
						+ trId,
				null);
		return cursor;
	}
}
