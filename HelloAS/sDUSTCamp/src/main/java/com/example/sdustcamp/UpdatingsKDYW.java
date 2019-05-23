package com.example.sdustcamp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class UpdatingsKDYW extends Activity {
	
	private TextView tv_titlekdyw;
	private TextView tv_timekdyw;
	private TextView tv_contentkdyw;
	private TextView tv_kdyw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.updating_kdyw);
		
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
		tv_kdyw = (TextView)findViewById(R.id.tv_kdyw);
		tv_timekdyw = (TextView)findViewById(R.id.tv_timekdyw);
		tv_contentkdyw = (TextView)findViewById(R.id.tv_contentkdyw);
		tv_titlekdyw = (TextView)findViewById(R.id.tv_titlekdyw);
		//setOnClick
		tv_kdyw.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getX() <= tv_kdyw.getCompoundDrawables()[0].getBounds().width()) {
					Intent intent = new Intent(UpdatingsKDYW.this, UpdatingsActivity.class);
					startActivity(intent);
					finish();
				}
				return false;
			}
		});
		Log.i("hehehe","12131232323");
		
		Intent intent = getIntent();
		Updatings node = intent.getParcelableExtra("Updatings");
		tv_titlekdyw.setText(node.getTitle().toString());
		tv_timekdyw.setText("时间 " + node.getTime().toString() + " · 点击量 " + node.getRate().toString());
		tv_contentkdyw.setText(node.getContext().toString());
		tv_contentkdyw.setMovementMethod(ScrollingMovementMethod.getInstance());
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
			
		}
		
	}
}
