package com.cling.mou;

import java.util.ArrayList;

import com.cling.mou.ClassActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/*历史记录主页面显示类
 * 功能实现：实现返回主菜单、显示历史记录条目、点击任一条目进入编辑界面、长点击任意条目实现多选删除
 * 1.调用ret_button监听类返回主页面
 * 2.数据库通过nodeInit()初始了5个数据，每次打开软件都进行初始化，连接添加功能之后删除即可
 */
public class MainActivity extends Activity {
	
	private Button ret_button;//返回主页面
	private Button txt_button;//文本按钮
	private Button aud_button;//音频按钮
	private TextView ret_title;//返回导航栏
	private MySQLiteOperator mySQLiteOperator;//数据库操作
	ArrayList<Node> source;
	public FileIOOperator fileOperator;
	
	public int itemId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		ret_button = (Button)findViewById(R.id.ret_button);
		txt_button = (Button)findViewById(R.id.txt_button);
		aud_button = (Button)findViewById(R.id.audio_button);
		ret_title = (TextView)findViewById(R.id.ret_title);
		
		fileOperator = new FileIOOperator();
		//打开或新建数据库
		mySQLiteOperator = new MySQLiteOperator(this);
		if(Node.id == 0)
			try {
				nodeInit();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		source = mySQLiteOperator.queryAllTxt();
		txt_button.setText("   文本备忘录     共"+source.size() + "项");
		source = mySQLiteOperator.queryAllAud();
		aud_button.setText("   语音备忘录     共"+source.size() + "项");
		
		ret_button.setOnClickListener(new ButtonListener());
		txt_button.setOnClickListener(new ButtonListener());
		aud_button.setOnClickListener(new ButtonListener());

		
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
	
	
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
			switch (view.getId()) {
			case R.id.ret_button://返回主菜单	
			break;
			case R.id.txt_button:
				Intent txt_intent = new Intent(MainActivity.this, ClassActivity.class);
				txt_intent.putExtra("id", 1);
				startActivity(txt_intent);
				finish();
			break;
			case R.id.audio_button:
				Intent aud_intent = new Intent(MainActivity.this, ClassActivity.class);
				aud_intent.putExtra("id", 0);
				startActivity(aud_intent);
				finish();
			break;
			}	
		}	
	}
	
	
	public void nodeInit() throws Throwable {
		// TODO Auto-generated method stub
		fileOperator.saveToSDCard(MainActivity.this, null, "/sdcard/mou/text/test_txt.txt", 1);
		Node node = new Node("test_txt", "/sdcard/mou/text/test_txt.txt", "1月2日", "2018年1月2日 22:49", 1);
		mySQLiteOperator.add(node);
		fileOperator.saveToSDCard(MainActivity.this, null, "/sdcard/mou/audio/test_aud.mp3", 2);
		node = new Node("test_aud", "/sdcard/mou/audio/test_aud.mp3", "2月1日", "2018年1月2日 22:49", 0);
		mySQLiteOperator.add(node);
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
	
}
