package com.example.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Btn_threeActivity extends Activity {

	private Button return_button_3;
	private RadioGroup rg_3;
	private RadioButton bt3_1;
	private RadioButton bt3_2;
	private RadioButton bt3_3;
	private RadioButton bt3_4;
	private RadioButton bt3_5;
	private RadioButton bt3_6;
	private RadioButton bt3_7;
	private MediaPlayer mp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_btn_three);
		
		return_button_3 = (Button)findViewById(R.id.return_button_3);
		rg_3 = (RadioGroup)findViewById(R.id.rg_3);
		bt3_1 = (RadioButton)findViewById(R.id.btn3_1);
		bt3_2 = (RadioButton)findViewById(R.id.btn3_2);
		bt3_3 = (RadioButton)findViewById(R.id.btn3_3);
		bt3_4 = (RadioButton)findViewById(R.id.btn3_4);
		bt3_5 = (RadioButton)findViewById(R.id.btn3_5);
		bt3_6 = (RadioButton)findViewById(R.id.btn3_6);
		bt3_7 = (RadioButton)findViewById(R.id.btn3_7);
		
		mp = MediaPlayer.create(Btn_threeActivity.this,R.raw.apera);
		return_button_3.setOnClickListener(new ButtonListener());
		rg_3.setOnCheckedChangeListener(new RadioGroupListener());
		
		String s = ProperUtil.getConfigProperties("3");
		switch(s) {
		case "woman":
			bt3_1.setChecked(true);
			break;
		case "man":
			bt3_2.setChecked(true);
			break;
		case "baby":
			bt3_3.setChecked(true);
			break;
		case "cat":
			bt3_4.setChecked(true);
			break;
		case "ufo":
			bt3_5.setChecked(true);
			break;
		case "alient":
			bt3_6.setChecked(true);
			break;
		case "gost":
			bt3_7.setChecked(true);
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
			case R.id.btn3_1:
				ProperUtil.writeDateToLocalFile("3","woman");
				play(R.raw.apera);
				break;
			case R.id.btn3_2:
				ProperUtil.writeDateToLocalFile("3","man");
				play(R.raw.apera);
				break;
			case R.id.btn3_3:
				ProperUtil.writeDateToLocalFile("3","baby");
				play(R.raw.apera);
				break;
			case R.id.btn3_4:
				ProperUtil.writeDateToLocalFile("3","cat");
				play(R.raw.apera);
				break;
			case R.id.btn3_5:
				ProperUtil.writeDateToLocalFile("3","ufo");
				play(R.raw.apera);
				break;
			case R.id.btn3_6:
				ProperUtil.writeDateToLocalFile("3","alient");
				play(R.raw.apera);
				break;
			case R.id.btn3_7:
				ProperUtil.writeDateToLocalFile("3","gost");
				play(R.raw.apera);
				break;
			}
			
		}
		
	}
	
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Btn_threeActivity.this, MainActivity.class);
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
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Btn_threeActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return false;
	}
	private void play(int id) {
		mp.reset();
		mp = MediaPlayer.create(Btn_threeActivity.this, id);
		mp.start();
	}
	protected void onDestroy() {
		if(mp.isPlaying()) {
			mp.stop();
		}
		mp.release();
		super.onDestroy();
	}
	
}
