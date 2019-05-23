package com.example.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class Btn_fiveActivity extends Activity {
	private Button return_button_5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_btn_five);

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
		
		return_button_5 = (Button)findViewById(R.id.return_button_5);
		return_button_5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Btn_fiveActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Btn_fiveActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return false;
	}
}
