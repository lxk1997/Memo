package com.cling.mou;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*数据库继承类
 */

public class MySQLiteUtil extends SQLiteOpenHelper {

	public static final String CREATE_TABLE_SQL = "create table user_tb(_id integer primary key autoincrement,title,content,date, time, istxt)";
	private Context mContext;
	public MySQLiteUtil(Context context, String name, CursorFactory factory, int version
			) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_SQL); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}
