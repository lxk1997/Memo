﻿package com.cling.memo;

import com.cling.memo.MainActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class ItemActivity extends Activity {
	
	private Button ret_button_1;//编辑界面返回按钮
	private Button edit_sure;//编辑界面确定按钮
	
	private Button delete_button;//删除按钮
	private Button share_button;//分享按钮
	private Button collect_button;//收藏按钮
	
	private TextView ret_title_1;//编辑界面标题
	private TextView date;//时间日期
	private EditText editText;//内容编辑
	private MySQLiteOperator mySQLiteOperator;//数据库操作
	private Node node;
	private int nid;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
//		MobSDK.init(this,"2748ffc414495","7a4b5e2190a3c12b404da0ad64675ef4");
		//ShareSDK.initSDK(this);
		ret_button_1 = (Button)findViewById(R.id.ret_button_1);
		edit_sure = (Button)findViewById(R.id.edit_sure);	
		delete_button = (Button)findViewById(R.id.delete);
		share_button = (Button)findViewById(R.id.share);
		collect_button = (Button)findViewById(R.id.collect);
		
		ret_title_1 = (TextView)findViewById(R.id.ret_title_1);
		editText = (EditText)findViewById(R.id.editText);
		date = (TextView)findViewById(R.id.date);
		
		ret_button_1.setOnClickListener(new ButtonListener());
		edit_sure.setOnClickListener(new ButtonListener());
		delete_button.setOnClickListener(new ButtonListener());
		share_button.setOnClickListener(new ButtonListener());
		collect_button.setOnClickListener(new ButtonListener());
		
		EditClickListener editListener = new EditClickListener();
		editText.setOnClickListener(editListener);
		
		// 打开或创建数据库
		mySQLiteOperator = new MySQLiteOperator(this);
		nid = getIntent().getIntExtra("id", 0);
		node = mySQLiteOperator.query(nid);
		date.setText(node.getTime());
		editText.setText(node.getContent());
//	
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
	
	//按钮点击事件
	class ButtonListener implements OnClickListener {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.ret_button_1:
				Intent intent_ret = new Intent(ItemActivity.this, MainActivity.class); 
				startActivity(intent_ret);
				finish();
				break;
			case R.id.edit_sure:
				Intent intent_sure = new Intent(ItemActivity.this, MainActivity.class);
				node.setContent(editText.getText().toString());
				mySQLiteOperator.update(node, nid);
				startActivity(intent_sure);
				finish();
				break;
			case R.id.delete:
				Intent intent_delete = new Intent(ItemActivity.this, MainActivity.class);
				mySQLiteOperator.delete(nid);
				Toast.makeText(ItemActivity.this, "备忘录删除成功", Toast.LENGTH_SHORT).show();
				startActivity(intent_delete);
				finish();
//			case R.id.share:
//				Intent intent_delete = new Intent(ItemActivity.this, MainActivity.class);
//				mySQLiteOperator.delete(nid);
//				Toast.makeText(ItemActivity.this, "备忘录删除成功", Toast.LENGTH_SHORT).show();
//				startActivity(intent_delete);
//				finish();
//			case R.id.collect:
//				Intent intent_delete = new Intent(ItemActivity.this, MainActivity.class);
//				mySQLiteOperator.delete(nid);
//				Toast.makeText(ItemActivity.this, "备忘录删除成功", Toast.LENGTH_SHORT).show();
//				startActivity(intent_delete);
//				finish();
			}
		}
	}
	
	//编辑栏点击事件
	class EditClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ret_title_1.setText("编辑备忘录");
			ret_title_1.setGravity(Gravity.CENTER);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ret_title_1.getLayoutParams();
			params.width = dip2px(ItemActivity.this, 260);
			params.height = dip2px(ItemActivity.this, 50);
			ret_title_1.setLayoutParams(params);
			editText.setFocusable(true);
			editText.setCursorVisible(true);
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
			
		}

	}
	
	//dp转px
	public int dip2px(Context context,float dipValue) 
	{
		Resources r = context.getResources();
		return (int) (TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics()) + 0.5f);
	}
	
//	public void ba(View v) {
//		OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
// 
//        // title标题：微信、QQ（新浪微博不需要标题）
//        oks.setTitle("我是分享标题");  //最多30个字符
// 
//        // text是分享文本：所有平台都需要这个字段
//        oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符
// 
//        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
//        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片
// 
//        //网络图片的url：所有平台
//        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
// 
//        // url：仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情
// 
//        // Url：仅在QQ空间使用
//        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情
// 
//        // 启动分享GUI
//        oks.show(this);
//    }
	//}

}
