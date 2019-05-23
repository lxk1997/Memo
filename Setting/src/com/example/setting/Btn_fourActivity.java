package com.example.setting;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Btn_fourActivity extends Activity {
    private List<String> paths = null;
    private String rootPath = "/sdcard";
    private String curPath;
    private TextView mPath;
    private Button return_button_4;
    private ListView lv;
    private FileDirListAdapter fileDirAdapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_btn_four);
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
        
        curPath = ProperUtil.getConfigProperties("4");
        
        return_button_4 = (Button)findViewById(R.id.return_button_4);
        lv = (ListView)findViewById(R.id.lv);
        mPath = (TextView) findViewById(R.id.editPath);
        mPath.setText(curPath);
        mPath.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFileDir(new File(curPath).getParent());
            }
        });
        mPath.setEnabled(true);
        Button buttonConfirm = (Button) findViewById(R.id.pathButton);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(Btn_fourActivity.this, "文件目录保存成功", Toast.LENGTH_SHORT).show();
            	ProperUtil.writeDateToLocalFile("4", mPath.getText().toString());
            	return_button_4.callOnClick();
            }
        });
        return_button_4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Btn_fourActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
        lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TextView tv = (TextView)view.findViewById(R.id.text);
				curPath = mPath.getText().toString() + '/' + tv.getText().toString();
				getFileDir(new File(curPath).getPath());
			}
		});
        paths = new ArrayList<String>();
        getFileDir(curPath);
    }
 
 
    private void getFileDir(String filePath) {
        paths.clear();
        mPath.setText(curPath = filePath);
        //设置向上是否可用
        if (filePath.equals(rootPath))
            mPath.setEnabled(false);
        else
            mPath.setEnabled(true);
        File f = new File(filePath);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            //过滤一遍
            //1.是否为文件夹
            //2.是否可访问
            if (files[i].isDirectory() && files[i].listFiles() != null) {
                paths.add(files[i].getPath());
            }
        }
        fileDirAdapter = new FileDirListAdapter(this, paths);
        lv.setAdapter(fileDirAdapter);
    }
 
    protected void onListItemClick(ListView l, View v, int position, long id) {
        this.getFileDir(paths.get(position));
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
			Intent intent = new Intent(Btn_fourActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return false;
	}
 
}
