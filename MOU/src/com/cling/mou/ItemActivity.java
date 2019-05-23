package com.cling.mou;

import java.io.File;

import com.mob.MobSDK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.onekeyshare.OnekeyShare;

/*备忘录编辑文本类
 * 功能实现：实现返回历史记录页面、编辑备忘录标题及内容、收藏、分享、删除
 * 收藏功能默认收藏到手机文件，连接个人中心后将收藏内容保存到个人中心
 */

public class ItemActivity extends Activity {
	
	private Button ret_button_1;//返回历史记录
	private Button edit_sure;//完成编辑
	
	private Button delete_button;//删除
	private Button share_button;//分享
	private Button collect_button;//收藏
	
	private TextView ret_title_1;//返回导航栏
	private TextView date;//日期
	private EditText editText;//编辑备忘录
	private EditText titleText;//编辑标题
	private MySQLiteOperator mySQLiteOperator;//数据库操作
	private MusicService musicService;
	private SeekBar playBar;
	private Button musicStart;
	private Node node;
	private FileIOOperator fileOperator;
	private int nid;
	private File f;
	private String path;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_item);

		ret_button_1 = (Button)findViewById(R.id.ret_button_1);
		edit_sure = (Button)findViewById(R.id.edit_sure);	
		delete_button = (Button)findViewById(R.id.delete);
		share_button = (Button)findViewById(R.id.share);
		collect_button = (Button)findViewById(R.id.collect);
		musicStart = (Button)findViewById(R.id.music_start);
		playBar = (SeekBar)findViewById(R.id.play_bar);
		
		ret_title_1 = (TextView)findViewById(R.id.ret_title_1);
		editText = (EditText)findViewById(R.id.editText);
		titleText = (EditText)findViewById(R.id.titleText);
		date = (TextView)findViewById(R.id.date);
		
		ret_button_1.setOnClickListener(new ButtonListener());
		edit_sure.setOnClickListener(new ButtonListener());
		delete_button.setOnClickListener(new ButtonListener());
		share_button.setOnClickListener(new ButtonListener());
		collect_button.setOnClickListener(new ButtonListener());
		EditClickListener editListener = new EditClickListener();
		editText.setOnClickListener(editListener);
		titleText.setOnClickListener(editListener);
		
		
		//新建或打开数据库
		mySQLiteOperator = new MySQLiteOperator(this);
		
		nid = getIntent().getIntExtra("id", 0);
		node = mySQLiteOperator.query(nid);
		date.setText(node.getTime());
		titleText.setText(node.getTitle());
		fileOperator = new FileIOOperator();
		
	
		path = "/sdcard/mou/collect/text/" + node.getTitle().toString() + nid + ".txt";
		editText.setVisibility(editText.VISIBLE);
		editText.setText(fileOperator.loadTxtFromSDFile(ItemActivity.this, node.getContent().toString()));
		playBar.setVisibility(playBar.GONE);
		musicStart.setVisibility(musicStart.GONE);
		
		Drawable topShare = getResources().getDrawable(R.drawable.ic_share);  		  
		share_button.setCompoundDrawablesWithIntrinsicBounds(null, topShare, null, null);
		Drawable topDelete = getResources().getDrawable(R.drawable.ic_delete);  		  
		delete_button.setCompoundDrawablesWithIntrinsicBounds(null, topDelete, null, null);
		
		f = new File(path);
		
		collect_button.setText((!f.exists() ? "收藏" : "取消收藏"));
		if(f.exists()) {
			Drawable topCollect = getResources().getDrawable(R.drawable.ic_qcollect);  		  
			collect_button.setCompoundDrawablesWithIntrinsicBounds(null, topCollect, null, null);
		} else {
			Drawable topCollect = getResources().getDrawable(R.drawable.ic_collect);  		  
			collect_button.setCompoundDrawablesWithIntrinsicBounds(null, topCollect, null, null);
		}
		MobSDK.init(this,"2748ffc414495","7a4b5e2190a3c12b404da0ad64675ef4");
		
		
		
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
	
	//按钮监听
	class ButtonListener implements OnClickListener {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.ret_button_1:
				Intent intent_ret = new Intent(ItemActivity.this, ClassActivity.class); 
				intent_ret.putExtra("id", 1);
				startActivity(intent_ret);
				finish();
				break;
			case R.id.edit_sure:
				if(ret_title_1.getText().toString().equals("历史记录")) break;
				Intent intent_sure = new Intent(ItemActivity.this, ClassActivity.class);
				node.setTitle(titleText.getText().toString());
				mySQLiteOperator.update(node, nid);
				File file = new File(node.getContent().toString());
				fileOperator.deleteFromSD(file);
				fileOperator.writeIntoSD(node.getContent().toString(), editText.getText().toString());
				intent_sure.putExtra("id", 1);
				startActivity(intent_sure);
				finish();
				break;
			case R.id.delete:
				Intent intent_delete = new Intent(ItemActivity.this, ClassActivity.class);
				Node nod = mySQLiteOperator.query(nid);
				File fil = new File(nod.getContent().toString());
				if(fil.exists()) fil.delete();
				mySQLiteOperator.delete(nid);
				Toast.makeText(ItemActivity.this, "删除备忘录成功", Toast.LENGTH_SHORT).show();
				intent_delete.putExtra("id", 1);
				startActivity(intent_delete);
				finish();
				break;
			case R.id.share:
				showShare();
				break;
			case R.id.collect:
				if(!f.exists()) {
					try {
						fileOperator.saveToSDCard(ItemActivity.this, node.getContent().toString(), path, 3);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Toast.makeText(ItemActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
					collect_button.setText("取消收藏");
					Drawable topCollect = getResources().getDrawable(R.drawable.ic_qcollect);  		  
					collect_button.setCompoundDrawablesWithIntrinsicBounds(null, topCollect, null, null);
				}
				else {
					fileOperator.deleteFromSD(f);
					Toast.makeText(ItemActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
					collect_button.setText("收藏");
					Drawable topCollect = getResources().getDrawable(R.drawable.ic_collect);  		  
					collect_button.setCompoundDrawablesWithIntrinsicBounds(null, topCollect, null, null);
				}
				break;
			}
		}
	}
	
	//编辑矿监听
	class EditClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ret_title_1.setText("编辑备忘录");
			ret_title_1.setGravity(Gravity.CENTER);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ret_title_1.getLayoutParams();
			params.width = dip2px(ItemActivity.this, 260);
			params.height = dip2px(ItemActivity.this, 50);
			ret_title_1.setLayoutParams(params);
			if(v.getId() == R.id.editText) {
				editText.setFocusable(true);
				editText.setCursorVisible(true);
				editText.setFocusableInTouchMode(true);
				editText.requestFocus();
			}
			else if(v.getId() == R.id.titleText) {
				titleText.setFocusable(true);
				titleText.setCursorVisible(true);
				titleText.setFocusableInTouchMode(true);
				titleText.requestFocus();
			}
			
		}

	}
	
	//dp2px
	public int dip2px(Context context,float dipValue) 
	{
		Resources r = context.getResources();
		return (int) (TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics()) + 0.5f);
	}
	
	
	private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(node.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("https://www.baidu.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(fileOperator.loadTxtFromSDFile(ItemActivity.this, node.getContent().toString()));
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory()+ "/xx/a.jpg");//确保SDcard下面存在此张图片
        oks.setUrl("https://www.baidu.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite("test");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("https://www.baidu.com/");
 
// 启动分享GUI
        oks.show(this);
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
			Intent intent = new Intent(ItemActivity.this, ClassActivity.class);
			intent.putExtra("id", 1);
		    startActivity(intent);
		    finish();
		}
		return false;
	}
	
}