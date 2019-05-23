package com.example.sdustcamp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginInActivity extends Activity {
	
	private TextView tv_title;
	private Button btn_regist;
	private EditText et_id;
	private EditText et_key;
	
	private Button btn_loginIn;
	private MySQLiteOperator mySqliteOperator;
	//private BmobOperator bmobOperator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
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
		tv_title = (TextView)findViewById(R.id.tv_title);
		btn_regist = (Button)findViewById(R.id.btn_regist);
		et_id = (EditText)findViewById(R.id.et_id);
		et_key = (EditText)findViewById(R.id.et_key);
		btn_loginIn = (Button)findViewById(R.id.btn_loginIn);
		mySqliteOperator = new MySQLiteOperator(this);
		
		//bmobOperator = new BmobOperator();
		
		//setOnClick
		btn_regist.setOnClickListener(new ButtonClickListener());
		btn_loginIn.setOnClickListener(new ButtonClickListener());
		tv_title.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getX() <= tv_title.getCompoundDrawables()[0].getBounds().width()) {
					Intent intent = new Intent(LoginInActivity.this, MineActivity.class);
					startActivity(intent);
					finish();
				}
				return false;
			}
		});
		
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
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btn_regist:
				intent = new Intent(LoginInActivity.this, RegistActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.btn_loginIn:
				String phoneNum = et_id.getText().toString().trim();
				String phoneKey = et_key.getText().toString().trim();
				//bmobOperator.queryAll(LoginInActivity.this, phoneNum);
				//bmobOperator.queryAll(LoginInActivity.this, phoneNum);
				//if(!bmobOperator.getIsRegisted()) {
				//	Toast.makeText(LoginInActivity.this, "该手机号未注册", Toast.LENGTH_SHORT).show();
				//	et_id.setText("");
				//	et_key.setText("");
				//} else if(!bmobOperator.getPassword().equals(phoneKey)) {
				//	Toast.makeText(LoginInActivity.this, "请输入正确的账号密码", Toast.LENGTH_SHORT).show();
				//	et_id.setText("");
				//	et_key.setText("");
					intent = new Intent(LoginInActivity.this, MineActivity.class);
					startActivity(intent);
					finish();
			//	} else {
				//	Toast.makeText(LoginInActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
				//	intent = new Intent(LoginInActivity.this, MineActivity.class);
				//	startActivity(intent);
				//	finish();
			//	}
				break;
			}
		}
		
	}
}
