package com.example.setting;

import java.io.File;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private Button B2;
    private Button B3;
    private Button B4;
    private Button B5;
    private Button B1;
    
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
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
		
		B1=(Button)findViewById(R.id.btn1);
		B2=(Button)findViewById(R.id.btn2);
		B3=(Button)findViewById(R.id.btn3);
		B4=(Button)findViewById(R.id.btn4);
		B5=(Button)findViewById(R.id.btn5);
		setListeners();
		
		File f = new File("/sdcard/mou/config.properties");
		if(!f.exists())configureInit();
	}
	
	private void setListeners(){
        OnClick onClick=new OnClick();
        B1.setOnClickListener(onClick);
        B2.setOnClickListener(onClick);
        B3.setOnClickListener(onClick);
        B4.setOnClickListener(onClick);
        B5.setOnClickListener(onClick);
    }
	private class OnClick implements View.OnClickListener{

        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
            	case R.id.btn1:
            		intent=new Intent(MainActivity.this,Btn_oneActivity.class);
            		break;
                case R.id.btn2:
                    intent=new Intent(MainActivity.this,Btn_twoActivity.class);
                    break;
                case R.id.btn3:
                    intent=new Intent(MainActivity.this, Btn_threeActivity.class);
                    break;
                case R.id.btn4:
                    intent=new Intent(MainActivity.this, Btn_fourActivity.class);
                    break;
                case R.id.btn5:
                    intent=new Intent(MainActivity.this,Btn_fiveActivity.class);
                    break;
            }
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
	
	@SuppressLint("SdCardPath")
	private void configureInit() {
		ProperUtil.writeDateToLocalFile("1", "note");//默认文本模式
		ProperUtil.writeDateToLocalFile("2", "shake");//默认提示震动
		ProperUtil.writeDateToLocalFile("3", "woman");//默认语音女生
		ProperUtil.writeDateToLocalFile("4", "/sdcard/mou");//默认文件保存路径
	}
}
