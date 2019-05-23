package com.cling.mou;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/*文本备忘录类
 */

public class ClassActivity extends Activity implements OnItemClickListener, OnItemLongClickListener{
	public int operatorId = 1;//1文本 0音频
	private Button classret_button;//返回主页面
	private Button cancel_button;//取消全选操作
	private Button delete_button;//删除
	private Button selectAll_button;//全选
	private TextView classret_title;//返回导航栏
	private ListView txt_list;//备忘录表
	private MySQLiteOperator mySQLiteOperator;//数据库操作
	ArrayList<Node> source;
	private Set<Integer> node_delete;
	private NodeAdapter mAdapter;//适配器
	private Boolean isMultiSelect;//是否多选
	private CheckBox cb;
	private FileIOOperator fileOperator;
	
	public int itemId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_class);
		
		classret_button = (Button)findViewById(R.id.classret_button);
		cancel_button = (Button)findViewById(R.id.cancel_button);
		delete_button = (Button)findViewById(R.id.deleteall_button);
		selectAll_button = (Button)findViewById(R.id.selectall_button);
		classret_title = (TextView)findViewById(R.id.classret_title);
		txt_list = (ListView)findViewById(R.id.ctxt_list);
		fileOperator = new FileIOOperator();
		
		operatorId = getIntent().getIntExtra("id",0);
		node_delete = new HashSet<Integer>();
		isMultiSelect = false;
		if(operatorId == 1) classret_title.setText("文本备忘录");
		else classret_title.setText("语音备忘录");
		
		//打开或新建数据库
		mySQLiteOperator = new MySQLiteOperator(this);

		classret_button.setOnClickListener(new ButtonListener());
		cancel_button.setOnClickListener(new ButtonListener());
		delete_button.setOnClickListener(new ButtonListener());
		selectAll_button.setOnClickListener(new ButtonListener());
		
		txt_list.setOnItemClickListener(this);
		txt_list.setOnItemLongClickListener(this);

		if(operatorId == 1) source = mySQLiteOperator.queryAllTxt();
		else source = mySQLiteOperator.queryAllAud();
		mAdapter = new NodeAdapter(ClassActivity.this,R.layout.node_item, source);
		mAdapter.operatorId = operatorId;
		txt_list.setAdapter(mAdapter);
		
		//系统状态栏透明
		Window window = this.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = getStatusBarHeight(window.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.parseColor("#6bd5e6"));
        decorViewGroup.addView(statusBarView);
//        try {
//			nodeInit();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
	
	
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
			switch (view.getId()) {
			case R.id.classret_button://返回主菜单
				Intent intent = new Intent(ClassActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			break;
			case R.id.cancel_button:
				isMultiSelect = false;
				if(operatorId == 1) classret_title.setText("文本备忘录");
				else classret_title.setText("语音备忘录");
				node_delete.clear();
				mAdapter = new NodeAdapter(ClassActivity.this,R.layout.node_item,source);
		        txt_list.setAdapter(mAdapter);
		        mAdapter.isShowCheckBox = false;
		        mAdapter.isChecked = false;
		        mAdapter.setCheckedId = -1;
		        mAdapter.operatorId = operatorId;
		        mAdapter.notifyDataSetChanged();
				cancel_button.setVisibility(8);
				delete_button.setVisibility(8);
				selectAll_button.setVisibility(8);
			break;
			case R.id.deleteall_button:
				//if(node_delete.size() == 0) continue;
				for(Integer i : node_delete) {
					Node nod = mySQLiteOperator.query(i);
					File fil = new File(nod.getContent().toString());
					if(fil.exists()) fil.delete();
					mySQLiteOperator.delete(i);
				}
				Toast.makeText(ClassActivity.this, "删除备忘录成功", Toast.LENGTH_SHORT).show();
				if(operatorId == 1) source = mySQLiteOperator.queryAllTxt();
				else source = mySQLiteOperator.queryAllAud();
				cancel_button.callOnClick();
			break;
			case R.id.selectall_button:
				//当前状态不是全选
				if(node_delete.size() < txt_list.getCount()) {
					for(int i = 0; i < txt_list.getCount(); i++) {
						Node node = source.get(i);
						int nid = mySQLiteOperator.seartch(node);
						node_delete.add(nid);
					}
					mAdapter = new NodeAdapter(ClassActivity.this,R.layout.node_item,source);
			        txt_list.setAdapter(mAdapter);
			        mAdapter.isShowCheckBox = true;
			        mAdapter.isChecked = true;
			        mAdapter.setCheckedId = -1;
			        mAdapter.operatorId = operatorId;
			        mAdapter.notifyDataSetChanged();
					classret_title.setText("共选择了"+ node_delete.size()+"项");
					selectAll_button.setText("取消全选");
					Drawable topSelect = getResources().getDrawable(R.drawable.ic_qselect);  		  
					selectAll_button.setCompoundDrawablesWithIntrinsicBounds(null, topSelect, null, null);
				} else {
					node_delete.clear();
					mAdapter = new NodeAdapter(ClassActivity.this,R.layout.node_item,source);
			        txt_list.setAdapter(mAdapter);
			        mAdapter.isShowCheckBox = true;
			        mAdapter.isChecked = false;
			        mAdapter.setCheckedId = -1;
			        mAdapter.operatorId = operatorId;
			        mAdapter.notifyDataSetChanged();
					classret_title.setText("共选择了"+ node_delete.size()+"项");
					selectAll_button.setText("全选");
					Drawable topSelect = getResources().getDrawable(R.drawable.ic_select);  		  
					selectAll_button.setCompoundDrawablesWithIntrinsicBounds(null, topSelect, null, null);
				}
			break;
			}	
		}	
	}
	
	
	//点击list_item，获得数据库中该item的_id，传给下一个activity
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(!isMultiSelect) {
			Node node = source.get(position);
			int nid = mySQLiteOperator.seartch(node);
			Intent intent;
			if(node.isText() == 1) {
				intent = new Intent(ClassActivity.this, ItemActivity.class);
			} else {
				intent = new Intent(ClassActivity.this, ItemActivityMedia.class);
			}
			intent.putExtra("id", nid);
			startActivity(intent);
			finish();
		} else {
			cb = (CheckBox)view.findViewById(R.id.cb_select);
			Node node = source.get(position);
			int nid = mySQLiteOperator.seartch(node);
			if(cb.isChecked()) {
				cb.setChecked(false);
				node_delete.remove(nid);
			} else {
				cb.setChecked(true);
				node_delete.add(nid);
			}
			if(node_delete.size() != 0) delete_button.setClickable(true);
			else delete_button.setClickable(false);
			classret_title.setText("共选择了"+ node_delete.size()+"项");
			if(node_delete.size() == txt_list.getCount()) {
				selectAll_button.setText("取消全选");
				Drawable topSelect = getResources().getDrawable(R.drawable.ic_qselect);  		  
				selectAll_button.setCompoundDrawablesWithIntrinsicBounds(null, topSelect, null, null);
			} else {
				selectAll_button.setText("全选");
				Drawable topSelect = getResources().getDrawable(R.drawable.ic_select);  		  
				selectAll_button.setCompoundDrawablesWithIntrinsicBounds(null, topSelect, null, null);
			}
		}
		return;
    }
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(isMultiSelect) return true;
		isMultiSelect = true;
		mAdapter = new NodeAdapter(ClassActivity.this,R.layout.node_item,source);
        txt_list.setAdapter(mAdapter);
        mAdapter.isShowCheckBox = true;
        mAdapter.isChecked = false;
        mAdapter.setCheckedId = position;
        mAdapter.notifyDataSetChanged();
		// 根据position，设置ListView中对应的CheckBox为选中状态
		cancel_button.setVisibility(0);
		delete_button.setVisibility(0);
		selectAll_button.setVisibility(0);
		Drawable topDelete = getResources().getDrawable(R.drawable.ic_delete);  		  
		delete_button.setCompoundDrawablesWithIntrinsicBounds(null, topDelete, null, null);
		Node node = source.get(position);
		int nid = mySQLiteOperator.seartch(node);
		node_delete.add(nid);
		classret_title.setText("共选择了"+ node_delete.size()+"项");
		if(node_delete.size() == txt_list.getCount()) {
			selectAll_button.setText("取消全选");
			Drawable topSelect = getResources().getDrawable(R.drawable.ic_qselect);  		  
			selectAll_button.setCompoundDrawablesWithIntrinsicBounds(null, topSelect, null, null);
		} else {
			selectAll_button.setText("全选");
			Drawable topSelect = getResources().getDrawable(R.drawable.ic_select);  		  
			selectAll_button.setCompoundDrawablesWithIntrinsicBounds(null, topSelect, null, null);
		}
		return true;
	}
	
	private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
	
	//重写onKeyDown方法,对按键监听
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
	            Intent intent = new Intent(ClassActivity.this, MainActivity.class);
	            startActivity(intent);
	            finish();
	        }
	        return false;
	}
}
