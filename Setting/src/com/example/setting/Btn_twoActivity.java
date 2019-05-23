package com.example.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Btn_twoActivity extends Activity {
	private Button return_button_2;
	private RadioGroup rg_1;
	private RadioButton btn2_1;
	private RadioButton btn2_3;
	private RadioButton btn2_4;
	private RadioButton btn2_5;
	private RadioButton btn2_6;
	private RadioButton btn2_7;
	private RadioButton btn2_8;
	private MediaPlayer mp;
	private Vibrator vibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_btn_two);
		
		return_button_2 = (Button)findViewById(R.id.return_button_2);
		rg_1 = (RadioGroup)findViewById(R.id.rg_1);
		btn2_1 = (RadioButton)findViewById(R.id.btn2_1);
		btn2_3 = (RadioButton)findViewById(R.id.btn2_3);
		btn2_4 = (RadioButton)findViewById(R.id.btn2_4);
		btn2_5 = (RadioButton)findViewById(R.id.btn2_5);
		btn2_6 = (RadioButton)findViewById(R.id.btn2_6);
		btn2_7 = (RadioButton)findViewById(R.id.btn2_7);
		btn2_8 = (RadioButton)findViewById(R.id.btn2_8);
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		
		mp = MediaPlayer.create(Btn_twoActivity.this,R.raw.apera);
		return_button_2.setOnClickListener(new ButtonListener());
		rg_1.setOnCheckedChangeListener(new RadioGroupListener());
		
		String s = ProperUtil.getConfigProperties("2");
		switch(s) {
		case "shake":
			btn2_1.setChecked(true);
			break;
		case "mute":
			btn2_3.setChecked(true);
			break;
		case "apera":
			btn2_4.setChecked(true);
			break;
		case "cere":
			btn2_5.setChecked(true);
			break;
		case "kalin":
			btn2_6.setChecked(true);
			break;
		case "pute":
			btn2_7.setChecked(true);
			break;
		case "qure":
			btn2_8.setChecked(true);
			break;
		}
		
		
		
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
	
	class RadioGroupListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch(checkedId) {
			case R.id.btn2_1:
				ProperUtil.writeDateToLocalFile("2","shake");
				mp.reset();
				vibrator.vibrate(1000);
				break;
			case R.id.btn2_3:
				ProperUtil.writeDateToLocalFile("2","mute");
				mp.reset();
				break;
			case R.id.btn2_4:
				ProperUtil.writeDateToLocalFile("2","apera");
				play(R.raw.apera);
				break;
			case R.id.btn2_5:
				ProperUtil.writeDateToLocalFile("2","cere");
				play(R.raw.cere);
				break;
			case R.id.btn2_6:
				ProperUtil.writeDateToLocalFile("2","kalin");
				play(R.raw.kalin);
				break;
			case R.id.btn2_7:
				ProperUtil.writeDateToLocalFile("2","pute");
				play(R.raw.pute);
				break;
			case R.id.btn2_8:
				ProperUtil.writeDateToLocalFile("2","qure");
				play(R.raw.qure);
				break;
			}
			
		}
		
	}
	
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mp.reset();
			Intent intent = new Intent(Btn_twoActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		
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
	
	private void play(int id) {
		mp.reset();
		mp = MediaPlayer.create(Btn_twoActivity.this, id);
		mp.start();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			mp.reset();
			Intent intent = new Intent(Btn_twoActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return false;
		
	}
	
	protected void onDestroy() {
		if(mp.isPlaying()) {
			mp.stop();
		}
		mp.release();
		super.onDestroy();
	}
	
}
