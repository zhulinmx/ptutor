package com.google.prtutor.dao;

import com.google.prtutor.model.Tb_records;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
    
	public RecordDAO(Context context) {
		helper = new DBOpenHelper(context);
	}	
	public void insertRecord(Tb_records tb_record) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_records(_id,words) values(?,?)", new Object[] {tb_record.getid() , tb_record.getwords()});
		db.close();
	}
	
	public void deleteRecord() {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_records");
		db.close();
	}
	/**
	 * ��������
	 */
	public void insertData(String tempName) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_records(words) values('" + tempName + "')");
		db.close();
	}

	/**
	 * ģ����ѯ����
	 */
	public Cursor queryRecord(String tempName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, words from tb_records where words like '%" + tempName + "%' order by _id desc ", null);
        return cursor;
	}
	/**
	 * ������ݿ����Ƿ��Ѿ��и�����¼
	 */
	public boolean hasRecord(String tempName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select _id as _id, words from tb_records where words =?", new String[]{tempName});
		//�ж��Ƿ�����һ��
		return cursor.moveToNext();
	}

	

}
