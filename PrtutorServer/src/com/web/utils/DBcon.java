package com.web.utils;

import java.sql.*;

public class DBcon {
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try {
//			long start = System.currentTimeMillis();
//			// ����SQLite��JDBC
//			Class.forName("org.sqlite.JDBC");
//			// ����һ�����ݿ���zieckey.db�����ӣ���������ھ��ڵ�ǰĿ¼�´���֮
//			// ��ɫ����·��Ҫ��ȫСд����д�ᱨ��
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:/data/data/com.google.prtutor/databases/tutorDB.db");
//			long end = System.currentTimeMillis();
//			System.out.println("�������ݿ��ļ������Ӻķ�ʱ�䣺" + (end - start));
//			Statement stat = conn.createStatement();
//			ResultSet rs = stat.executeQuery("select * from tb_stu;"); // ��ѯ����
//			while (rs.next()) { // ����ѯ�������ݴ�ӡ����
//				System.out.print("stuname = " + rs.getString("stuname") + " ");
//			}
//			rs.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
