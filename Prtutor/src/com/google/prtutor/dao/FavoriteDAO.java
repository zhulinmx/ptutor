package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_favoriate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FavoriteDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public FavoriteDAO(Context context) {
		helper = new DBOpenHelper(context);// ��ʼ��

	}

	/**
	 * �Ƿ��ղ�
	 */
	public boolean hasFavorite(Tb_favoriate tb_favoriate) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_favoriate where name = '"
				+ tb_favoriate.getName() + "' and courseId =" + tb_favoriate.getCourseId(), null);
		// �ж��Ƿ�����һ��
		return cursor.moveToNext();
	}

	/**
	 * ����ղ�
	 */
	public void addFavorite(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_favoriate(courseId,name) values(?,?)",
				new Object[] { tb_favoriate.getCourseId(), tb_favoriate.getName() });
		db.close();
	}

	/**
	 * ȡ���ղ�
	 */
	public void deleteFavorite(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_favoriate where name = '" + tb_favoriate.getName() + "' and courseId ="
				+ tb_favoriate.getCourseId());
		db.close();
	}

	/**
	 * �ղص�����
	 */
	public Cursor displayFavorite(String name) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_course.courseName,tb_course.desc,tb_course.start_day,tb_course.img, tb_course.price, tb_course._id from tb_favoriate,tb_course where tb_favoriate.courseId=tb_course._id and tb_favoriate.name ='"
						+ name + "' ",
				null);
		return cursor;
	}

	/**
	 * �ܼ��ղؼ�����
	 */
	public int CountCollect(String stuname) {
		Cursor cursor = helper.getReadableDatabase()
				.rawQuery("select * from tb_favoriate where name = '" + stuname + "' ", null);
		int count = cursor.getCount();
		return count;
	}

	/**
	 * �Ƿ��ע
	 */
	public boolean hasLove(Tb_favoriate tb_favoriate) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_love where name = '"
				+ tb_favoriate.getName() + "' and teachId =" + tb_favoriate.getCourseId(), null);
		// �ж��Ƿ�����һ��
		return cursor.moveToNext();
	}

	/**
	 * ��ӹ�ע
	 */
	public void addLove(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_love(teachId,name) values(?,?)",
				new Object[] { tb_favoriate.getCourseId(), tb_favoriate.getName() });
		db.close();
	}

	/**
	 * ȡ����ע
	 */
	public void deleteLove(Tb_favoriate tb_favoriate) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_love where name = '" + tb_favoriate.getName() + "' and teachId ="
				+ tb_favoriate.getCourseId());
		db.close();
	}

	/**
	 * ��ע����ʦ
	 */
	public Cursor displayLove(String name) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_teacher.realName, tb_teacher._id, tb_teacher.image, tb_teacher.quotation from tb_love,tb_teacher where tb_love.teachId = tb_teacher._id and tb_love.name ='"
						+ name + "' ",
				null);
		return cursor;
	}

	/**
	 * �ܼƹ�ע��ѧ����
	 */
	public int CountLove(int teachId) {
		Cursor cursor = helper.getReadableDatabase().rawQuery("select * from tb_love where teachId =" + teachId, null);
		int count = cursor.getCount();
		return count;
	}

	/**
	 * ��ѯ��ʦtop10
	 */
	public Cursor queryTop() {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select tb_teacher.realName, tb_teacher.image, tb_teacher._id, tb_teacher.quotation from tb_love,tb_teacher where tb_love.teachId = tb_teacher._id group by tb_love.teachId order by count(tb_love.teachId)",
				null);
		return cursor;
	}
}
