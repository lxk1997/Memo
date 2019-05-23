package com.example.sdustcamp;

import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegistActivity extends Activity {
	
	private EditText et_phoneNum;
	private EditText et_phoneKey;
	private EditText et_phoneModify;
	private Button btn_phoneModify;
	private Button btn_phoneRegist;
	private Boolean is_true;
	
	private TextView tv_registTitle;
	private EventHandler eh;
	private MySQLiteOperator mySqlLiteOperator;
	//private BmobOperator bmobOperator;
	private CountDownTimerUtils myCountDownTimerUtils;
	TableUser p = new TableUser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//第一：默认初始化
        //Bmob.initialize(this, "dbd18b062cdaf305286133d99022b40d");
		setContentView(R.layout.activity_regist);
	
		
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
		tv_registTitle = (TextView)findViewById(R.id.tv_registTitle);
		et_phoneNum = (EditText)findViewById(R.id.et_phoneNum);
		et_phoneKey = (EditText)findViewById(R.id.et_phoneKey);
		et_phoneModify = (EditText)findViewById(R.id.et_phoneModify);
		btn_phoneModify = (Button)findViewById(R.id.btn_phoneModify);
		btn_phoneRegist = (Button)findViewById(R.id.btn_phoneRegist);
		
		myCountDownTimerUtils = new CountDownTimerUtils(btn_phoneModify, 30000, 1000);
		mySqlLiteOperator = new MySQLiteOperator(this);
		//bmobOperator = new BmobOperator();
		et_phoneNum.setText("");
    	et_phoneKey.setText("");
    	et_phoneModify.setText("");
		
		//setOnClick
		tv_registTitle.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getX() <= tv_registTitle.getCompoundDrawables()[0].getBounds().width()) {
					Intent intent = new Intent(RegistActivity.this, LoginInActivity.class);
					startActivity(intent);
					finish();
				}
				return false;
			}
		});
		btn_phoneModify.setOnClickListener(new ButtonClickListener());
		btn_phoneRegist.setOnClickListener(new ButtonClickListener());
		
		
		
		MobSDK.init(this, "27ab25ef673ca", "906d826efb97dc27ca6df4907ab66243");
		// 短信验证回调
		eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.i("EventHandler", "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成
                        Log.i("EventHandler", "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.i("EventHandler", "返回支持发送验证码的国家列表");
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        	//Toast.makeText(RegistActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                        	//is_true = false;
                        }
                    });
                    Log.i("EventHandler", "回调失败");
                }
            }
        };

        SMSSDK.registerEventHandler(eh);//注册短信回调接口
    }
		
    // 获取验证码
   public void GetCode(String PhoneNumber)
   {
   	SMSSDK.getVerificationCode("86", PhoneNumber);

   }

   // 提交验证码
   public void sendCode(String PhoneNumber,String VerifyCode)
   {
       SMSSDK.submitVerificationCode("86", PhoneNumber,VerifyCode);
   }

 //最后注销监听，否则可能会造成内存泄露，我这里随便找的位置调用的，就是在一次认证成功之后，可以放在关闭程序的时候调用
   public void unRegisterEventHandler()
   {
         SMSSDK.unregisterEventHandler(eh);
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
			String phoneNum = "1";
			String phoneKey = "1";
			switch(v.getId()) {
			case R.id.btn_phoneModify:
				phoneNum = et_phoneNum.getText().toString().trim();
				phoneKey = et_phoneKey.getText().toString().trim();
				if(phoneNum.length() != 11) {
					Toast.makeText(RegistActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
				} else if(phoneKey.length() < 8 || phoneKey.length() > 15) {
					Toast.makeText(RegistActivity.this, "密码长度不小于8位", Toast.LENGTH_SHORT).show();
				} else {
					GetCode(phoneNum);
					myCountDownTimerUtils.start();
				}
				break;
			case R.id.btn_phoneRegist:
				phoneNum = et_phoneNum.getText().toString().trim();
				phoneKey = et_phoneKey.getText().toString().trim();
				if(phoneNum.length() != 11) {
					Toast.makeText(RegistActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
				} else if(phoneKey.length() < 8 || phoneKey.length() > 15) {
					Toast.makeText(RegistActivity.this, "密码长度不小于8位", Toast.LENGTH_SHORT).show();
				} else {
					//is_true = true;
					//sendCode(phoneNum, et_phoneModify.getText().toString().trim());
					//if(is_true == false) break;
					//第二：新建一个User
					p.setPhoneNum(phoneNum);
					p.setPassword(phoneKey);
					//p.setObjectId(phoneNum)
			//		bmobOperator.queryAll(RegistActivity.this, phoneNum);
				//	bmobOperator.queryAll(RegistActivity.this, phoneNum);
			    //    if(bmobOperator.getIsRegisted()) {
			    //    	Toast.makeText(RegistActivity.this, "手机号已经被注册", Toast.LENGTH_SHORT).show();
			        	et_phoneNum.setText("");
			        	et_phoneKey.setText("");
			        	et_phoneModify.setText("");

			     //   } else {
			     //   	bmobOperator.add(RegistActivity.this, p);
			    //    	Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
			     //   	Intent intent = new Intent(RegistActivity.this, LoginInActivity.class);
				//		startActivity(intent);
				//		finish();
			     //   }
				}
				break;
			}
		}
		
	}
}
