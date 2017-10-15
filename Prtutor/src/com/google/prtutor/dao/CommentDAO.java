package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_comment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommentDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public CommentDAO(Context context) {
		helper = new DBOpenHelper(context);
	}

	/**
	 * 增加评论
	 */
	public void AddComment(Tb_comment tb_comment) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_comment(createTime,stuname,comment,courseId) values(?,?,?,?)",
				new Object[] { tb_comment.getCreateTime(), tb_comment.getStuName(), tb_comment.getComment(),
						tb_comment.getCourseId() });
		db.close();
	}

	/**
	 * 查询评论按(课程id)
	 */
	public Cursor queryComment(int courseId) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_comment.stuname, tb_comment.createTime, tb_comment.comment,tb_stu.image from tb_comment,tb_stu where tb_comment.stuname = tb_stu.stuname and tb_comment.courseId = "
						+ courseId,
				null);
		return cursor;
	}

	/**
	 * 查询评论按(学生姓名)
	 */
	public Cursor queryStuComment(String stuname) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_comment.createTime, tb_comment.comment, tb_course.courseName, tb_course.img from tb_comment, tb_course where tb_comment.courseId = tb_course._id and tb_comment.stuname = '"
						+ stuname + "'",
				null);
		return cursor;
	}

	/**
	 * 查询评论按(老师id)
	 */
	public Cursor queryTutorComment(int tutorId) {
		String sql = "select tb_comment.stuname, tb_comment.createTime, tb_comment.comment, tb_course.courseName, substr(tb_comment.createTime,7,2) from tb_comment,tb_course,tb_teacher where tb_comment.courseId = tb_course._id and tb_teacher._id = tb_course.teachId and tb_teacher._id = "
				+ tutorId;
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql + " order by tb_comment.createTime desc", null);
		return cursor;
	}
}
