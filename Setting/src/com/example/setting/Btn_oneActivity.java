package com.example.setting;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
 
public class Btn_oneActivity extends Activity {
	private RadioGroup mRg1;
	private Button return_button_1;
	private RadioButton bt1;
	private RadioButton bt2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_btn_one);
		
		
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
		
		return_button_1 = (Button)findViewById(R.id.return_button_1);
		mRg1=(RadioGroup)findViewById(R.id.rg1);
		bt1 = (RadioButton)findViewById(R.id.rb1);
		bt2 = (RadioButton)findViewById(R.id.rb2);
		
		String s = ProperUtil.getConfigProperties("1");
		switch(s) {
		case "note":
			bt1.setChecked(true);
			break;
		case "audio":
			bt2.setChecked(true);
			break;
		}
		
        //监听事件
        mRg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                case R.id.rb1:
                	ProperUtil.writeDateToLocalFile("1", "note");
                	break;
                case R.id.rb2:
                	ProperUtil.writeDateToLocalFile("1", "audio");
                	break;
                }
                Toast.makeText(Btn_oneActivity.this,"选择成功",Toast.LENGTH_SHORT).show();
            }
        }); 
        
        return_button_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Btn_oneActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	
    public String is_default;
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.rb1:
			is_default="0";
			break;
		case R.id.rb2:
			is_default="1";
			break;
		}
		ProperUtil.writeDateToLocalFile("1", is_default);
		
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
			Intent intent = new Intent(Btn_oneActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return false;
	}
}


    
    
   
