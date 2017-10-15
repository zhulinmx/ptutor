package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_favoriate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FavoriteDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public FavoriteDAO(Context context) {
		helper = new DBOpenHelper(context);// 初始化

	}

	/**
	 * 是否收藏
	 */
	public boolean hasFavorite(Tb_favoriate tb_favoriate) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_favoriate where name = '"
				+ tb_favoriate.getName() + "' and courseId =" + tb_favoriate.getCourseId(), null);
		// 判断是否有下一个
		return cursor.moveToNext();
	}

	/**
	 * 添加收藏
	 */
	public void addFavorite(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_favoriate(courseId,name) values(?,?)",
				new Object[] { tb_favoriate.getCourseId(), tb_favoriate.getName() });
		db.close();
	}

	/**
	 * 取消收藏
	 */
	public void deleteFavorite(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_favoriate where name = '" + tb_favoriate.getName() + "' and courseId ="
				+ tb_favoriate.getCourseId());
		db.close();
	}

	/**
	 * 收藏的内容
	 */
	public Cursor displayFavorite(String name) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_course.courseName,tb_course.desc,tb_course.start_day,tb_course.img, tb_course.price, tb_course._id from tb_favoriate,tb_course where tb_favoriate.courseId=tb_course._id and tb_favoriate.name ='"
						+ name + "' ",
				null);
		return cursor;
	}

	/**
	 * 总计收藏夹数量
	 */
	public int CountCollect(String stuname) {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select * from tb_favoriate where name = '" + stuname + "' ", null);
		int count = cursor.getCount();
		return count;
	}

	/**
	 * 是否关注
	 */
	public boolean hasLove(Tb_favoriate tb_favoriate) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_love where name = '"
				+ tb_favoriate.getName() + "' and teachId =" + tb_favoriate.getCourseId(), null);
		// 判断是否有下一个
		return cursor.moveToNext();
	}

	/**
	 * 添加关注
	 */
	public void addLove(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_love(teachId,name) values(?,?)",
				new Object[] { tb_favoriate.getCourseId(), tb_favoriate.getName() });
		db.close();
	}

	/**
	 * 取消关注
	 */
	public void deleteLove(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_love where name = '" + tb_favoriate.getName() + "' and teachId ="
				+ tb_favoriate.getCourseId());
		db.close();
	}

	/**
	 * 关注的老师
	 */
	public Cursor displayLove(String name) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_teacher.realName, tb_teacher._id, tb_teacher.image, tb_teacher.quotation from tb_love,tb_teacher where tb_love.teachId = tb_teacher._id and tb_love.name ='"
						+ name + "' ",
				null);
		return cursor;
	}

	/**
	 * 总计关注的学生数
	 */
	public int CountLove(int teachId) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_love where teachId =" + teachId, null);
		int count = cursor.getCount();
		return count;
	}

	/**
	 * 查询老师top10
	 */
	public Cursor queryTop() {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_teacher.realName, tb_teacher.image, tb_teacher._id, tb_teacher.quotation from tb_love,tb_teacher where tb_love.teachId = tb_teacher._id group by tb_love.teachId order by count(tb_love.teachId)",
				null);
		return cursor;
	}
}
