package com.example.sdustcamp;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineActivity extends Activity implements OnTouchListener, OnGestureListener{
	
	private Button btn_updatings;
	private Button btn_service;
	private Button btn_index;
	private Button btn_news;
	private Button btn_mine;
	
	private Button btn_setting;
	private TextView tv_signIn;
	
	private GestureDetector gd;
	private RelativeLayout rl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mine);
		
		//系统状态栏透明
		Window window = this.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
		View statusBarView = new View(window.getContext());
		int statusBarHeight = getStatusBarHeight(window.getContext());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
		params.gravity = Gravity.TOP;
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(Color.parseColor("#0359a2"));
		decorViewGroup.addView(statusBarView);

		//find
		btn_updatings = (Button)findViewById(R.id.btn_updatings);
		btn_service = (Button)findViewById(R.id.btn_service);
		btn_index = (Button)findViewById(R.id.btn_index);
		btn_news = (Button)findViewById(R.id.btn_news);
		btn_mine = (Button)findViewById(R.id.btn_mine);
		
		btn_setting = (Button)findViewById(R.id.btn_setting);
		tv_signIn = (TextView)findViewById(R.id.tv_signIn);
		
		//setOnClick
		btn_updatings.setOnClickListener(new ButtonClickListener());
		btn_service.setOnClickListener(new ButtonClickListener());
		btn_index.setOnClickListener(new ButtonClickListener());
		btn_news.setOnClickListener(new ButtonClickListener());
		btn_mine.setOnClickListener(new ButtonClickListener());
		
		btn_setting.setOnClickListener(new ButtonClickListener());
		tv_signIn.setOnClickListener(new ButtonClickListener());
		
		rl = (RelativeLayout)findViewById(R.id.rl_mine);
		gd = new GestureDetector(this, this);
		rl.setOnTouchListener(this);
		rl.setLongClickable(true);
		
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
	
	//获得状态栏高度
	private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
	
	//OnClickListener
	class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent  = null;
			switch (v.getId()) {
			case R.id.btn_updatings:
				intent = new Intent(MineActivity.this, UpdatingsActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.btn_service:
				intent = new Intent(MineActivity.this, ServiceActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.btn_index:
				intent = new Intent(MineActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.btn_news:
				intent = new Intent(MineActivity.this, NewsActivity.class);
				startActivity(intent);
				finish();
				break;
//			case R.id.btn_mine:
//				intent = new Intent(MineActivity.this, MineActivity.class);
//				startActivity(intent);
//				finish();
//				break;
			case R.id.btn_setting:
				intent = new Intent(MineActivity.this, SettingActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.tv_signIn:
				intent = new Intent(MineActivity.this, LoginInActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		final int FLING_MIN_DISTANCE = 100;
		final int FLING_MIN_VOLOCITY = 100;
		
		if(e1.getX() - e2.getX() < FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VOLOCITY) {
			Intent intent = new Intent(MineActivity.this, NewsActivity.class);
			startActivity(intent);
			finish();
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return gd.onTouchEvent(event);
	}
	
}
