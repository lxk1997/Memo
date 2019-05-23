﻿package com.cling.memo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cling.memo.MySQLiteUtil;
import com.cling.memo.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {
	
	private Button ret_button;//返回按钮
	private ListView txt_list;//文本列表
	private MySQLiteOperator mySQLiteOperator;//数据库操作
	ArrayList<Node> source;
	private NodeAdapter mAdapter;//适配器
	public int itemId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ret_button = (Button)findViewById(R.id.ret_button);
		txt_list = (ListView)findViewById(R.id.txt_list);
		
		// 打开或创建数据库
		mySQLiteOperator = new MySQLiteOperator(this);
		if(Node.id == 0)nodeInit();
		
		ReturnButtonListener returnListener = new ReturnButtonListener();
		ret_button.setOnClickListener(returnListener);
		
		txt_list.setOnItemClickListener(this);
		
		source = mySQLiteOperator.qureyall();
		mAdapter = new NodeAdapter(MainActivity.this,R.layout.node_item,source);
		txt_list.setAdapter(mAdapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	class ReturnButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			//////返回主页面
			
		}	
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
		Node node = source.get(position);
		int nid = mySQLiteOperator.seartch(node);
		Intent intent = new Intent(MainActivity.this, ItemActivity.class); 
		intent.putExtra("id", nid);
        startActivity(intent);
        finish();
		return;
    }
	
	public void nodeInit() {
		// TODO Auto-generated method stub
		Node node = new Node("熊大", "asdadasdsadasdasdasdasdasdasdasdasdasdasdasdasdasdasdsadasdasdsa", "1月2日", "2018年1月2日 22:49");
		mySQLiteOperator.add(node);
		node = new Node("俺", "qqqq", "2月1日", "2018年1月2日 22:49");
		mySQLiteOperator.add(node);
		node = new Node("要", "fdsfds", "2月3日", "2018年2月3日 22:49");
		mySQLiteOperator.add(node);
		node = new Node("吃", "jhjhgj", "2月4日", "2018年2月4日 22:49");
		mySQLiteOperator.add(node);
		node = new Node("蜂蜜", "cxzzxc", "2月5日", "2018年2月5日 22:49");
		mySQLiteOperator.add(node);
	}
}
