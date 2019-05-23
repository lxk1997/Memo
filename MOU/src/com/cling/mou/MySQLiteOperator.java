package com.cling.mou;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*数据库操作类
 * 功能实现：实现数据库增删改查功能
 */

public class MySQLiteOperator {

	private MySQLiteUtil mySQLiteUtil;//辅助数据库
	private SQLiteDatabase db;//数据库
	
	public MySQLiteOperator(Context context) {
		mySQLiteUtil = new MySQLiteUtil(context, "mozz.db", null,1);
		db = mySQLiteUtil.getWritableDatabase();
	}
	
	//add
	public void add(Node node) {
		db.execSQL("insert into user_tb values(null,?,?,?,?,?)", new Object[] {node.getTitle(), node.getContent(),
				node.getDate(), node.getTime(), node.isText()});
	}
	
	//update
	public void update(Node node, int id) {
		db.execSQL("update user_tb set title=?,content=?,date=?,time=?, istxt=? where _id=?",new Object[] {node.getTitle(), node.getContent(),
				node.getDate(), node.getTime(),node.isText(), id+""});
	}
	
	//delete
	public void delete(Object id) {
		db.execSQL("delete from user_tb where _id=?", new Object[] {id});
		
	}
	
	//seartch
	public int seartch(Node node) {
		Cursor cursor = db.rawQuery("select * from user_tb", null);
		while (cursor.moveToNext()) {
			if(cursor.getString(1).equals(node.getTitle()) &&  cursor.getString(2).equals(node.getContent())
					&& cursor.getString(3).equals(node.getDate()) && cursor.getString(4).equals(node.getTime()))
				return cursor.getInt(0);
		}
		cursor.close();
		return 0;
	}
	
	//query
	public Node query(int id) {
		Node node = new Node();
		Cursor cursor = db.rawQuery("select * from user_tb where _id=?", new String[] {id+""});
		while (cursor.moveToNext()) {
			node.setTitle(cursor.getString(1));
			node.setContent(cursor.getString(2));
			node.setDate(cursor.getString(3));
			node.setTime(cursor.getString(4));
			node.setisText(cursor.getInt(5));
		}
		cursor.close();
		return node;
	}
	
	//query all text
	public ArrayList<Node> queryAllTxt() {
		ArrayList<Node> records = new ArrayList<Node>();
		Cursor cursor = db.rawQuery("select * from user_tb", null);
		while (cursor.moveToNext()) {
			Node node = new Node();
			node.setTitle(cursor.getString(1));
			node.setContent(cursor.getString(2));
			node.setDate(cursor.getString(3));
			node.setTime(cursor.getString(4));
			node.setisText(cursor.getInt(5));
			if(node.isText() == 1)records.add(node);
		}
		cursor.close();
		return records;
	}
	
	//query all audio
	public ArrayList<Node> queryAllAud() {
		ArrayList<Node> records = new ArrayList<Node>();
		Cursor cursor = db.rawQuery("select * from user_tb", null);
		while (cursor.moveToNext()) {
			Node node = new Node();
			node.setTitle(cursor.getString(1));
			node.setContent(cursor.getString(2));
			node.setDate(cursor.getString(3));
			node.setTime(cursor.getString(4));
			node.setisText(cursor.getInt(5));
			if(node.isText() == 0)records.add(node);
		}
		cursor.close();
		return records;
	}
}
