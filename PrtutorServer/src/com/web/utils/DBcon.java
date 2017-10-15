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
//			// 连接SQLite的JDBC
//			Class.forName("org.sqlite.JDBC");
//			// 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
//			// 红色部分路径要求全小写，大写会报错
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:/data/data/com.google.prtutor/databases/tutorDB.db");
//			long end = System.currentTimeMillis();
//			System.out.println("创建数据库文件并连接耗费时间：" + (end - start));
//			Statement stat = conn.createStatement();
//			ResultSet rs = stat.executeQuery("select * from tb_stu;"); // 查询数据
//			while (rs.next()) { // 将查询到的数据打印出来
//				System.out.print("stuname = " + rs.getString("stuname") + " ");
//			}
//			rs.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
